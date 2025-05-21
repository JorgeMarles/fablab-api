package com.example.demo.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.request.DatosPersonalesDTO;
import com.example.demo.DTO.response.UserInfoDTO;
import com.example.demo.entities.Participante;
import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirebaseService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Create user in Firebase and in local database
    @Transactional
    public Usuario createUser(Usuario usuario, DatosPersonalesDTO dto) throws FirebaseAuthException {
        // Create user in Firebase
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(usuario.getCorreoPersonal())
                .setPassword(dto.getPassword())
                .setDisplayName(usuario.getNombreCompleto())
                .setEmailVerified(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        // Create user in our database with roles
        usuario.setUid(userRecord.getUid());

        return usuarioRepository.save(usuario);
    }

    public void changeEmail(String uid, String newEmail) throws FirebaseAuthException {
        // Change email in Firebase
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(newEmail);

        FirebaseAuth.getInstance().updateUser(request);
    }

    // Verify Firebase ID token
    public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    public UserInfoDTO getUserDataToken(String token) throws FirebaseAuthException {
        String uid = verifyToken(token).getUid();
        return getUserData(uid);
    }

    @Transactional
    public Usuario verificarDatos(FirebaseToken token) {
        String uid = token.getUid();
        String correo = token.getEmail();
        String nombre = token.getName();

        try {
            // First try to find by uid
            Optional<Usuario> usuarioUidOpt = usuarioRepository.findByUid(uid);
            if (usuarioUidOpt.isPresent()) {
                return usuarioUidOpt.get();
            }

            // Then try to find by email
            Optional<Usuario> usuarioCorreoOpt = usuarioRepository.findByCorreoPersonal(correo);
            if (usuarioCorreoOpt.isPresent()) {
                Usuario usuario = usuarioCorreoOpt.get();
                usuario.setUid(uid);
                return usuarioRepository.save(usuario);
            }

            // Create new if not found
            Usuario usuario = new Usuario();
            usuario.setUid(uid);
            usuario.setCorreoPersonal(correo);
            usuario.setNombreCompleto(nombre == null ? "" : nombre);

            Participante participante = new Participante();
            participante.setUsuario(usuario);
            usuario.setParticipante(participante);

            return usuarioRepository.save(usuario);

        } catch (DataIntegrityViolationException e) {
            // If constraint violation occurs, try again after a short delay
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            // Find the user that was created by another thread
            Optional<Usuario> retryUser = usuarioRepository.findByUid(uid);
            if (retryUser.isPresent()) {
                return retryUser.get();
            }

            retryUser = usuarioRepository.findByCorreoPersonal(correo);
            if (retryUser.isPresent()) {
                Usuario usuario = retryUser.get();
                if (usuario.getUid() == null) {
                    usuario.setUid(uid);
                    return usuarioRepository.save(usuario);
                }
                return usuario;
            }

            throw new RuntimeException("Failed to create or find user after constraint violation", e);
        }
    }

    // Get user data including roles
    public UserInfoDTO getUserData(String firebaseUid) {
        UserInfoDTO userData = new UserInfoDTO();

        usuarioRepository.findByUid(firebaseUid).ifPresent(user -> {
            userData.setNombre(user.getNombreCompleto());
            userData.setRoles(user.getRoles());
            userData.setHasPersonalData(user.getHasPersonalData());
        });

        return userData;
    }

    public Authentication getAuthentication(String token) throws FirebaseAuthException {
        FirebaseToken firebaseToken = verifyToken(token);
        return getAuthentication(firebaseToken);
    }

    // Create Spring Security Authentication from Firebase token
    public Authentication getAuthentication(FirebaseToken firebaseToken) {
        Usuario user = verificarDatos(firebaseToken);

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(
                user, firebaseToken, authorities);
    }

    public void changePassword(String uid, String newPassword) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setPassword(newPassword);

        FirebaseAuth.getInstance().updateUser(request);
    }
}

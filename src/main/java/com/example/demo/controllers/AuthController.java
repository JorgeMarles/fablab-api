package com.example.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
/* 
	@Autowired UserService userService;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginReq)  {
        try {
        	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            String email = authentication.getName();
            User user = userService.buscarUsuario(email);
            String token = jwtUtil.createToken(user);
            LoginResponseDTO loginRes = new LoginResponseDTO(token);

            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e){
        	throw new BadCredentialsException("Email o contraseña inválidos");
        }
    }
*/}
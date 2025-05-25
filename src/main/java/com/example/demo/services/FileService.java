package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Archivo;
import com.example.demo.entities.Usuario;
import com.example.demo.exceptions.FileException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ArchivoRepository;
import com.example.demo.utils.Pair;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

	@Value("${app.file-upload-dir:uploads}")
	private String uploadDirectory;

	@Value("${app.file-max-size:10485760}") // 10 MB
	private long maxFileSize;

	private static String staticFileEndpoint;

	@Value("${app.file-endpoint-files:/files}")
	private String fileEndpointFiles;

	@PostConstruct
	public void init() {
		// Initialize the static field after properties are set
		FileService.staticFileEndpoint = this.fileEndpointFiles;
	}

	// Static method to access the file endpoint
	public static String getFileEndpointFiles() {
		if (staticFileEndpoint == null) {
			// Fallback to default value if not initialized
			return "/files";
		}
		return staticFileEndpoint;
	}

	@Autowired
	private ArchivoRepository archivoRepository;

	private static final String[] ALLOWED_EXTENSIONS = { ".jpg", ".png", ".jpeg", ".bmp", ".gif", ".tiff", ".tif",
			".webp", ".ico", ".svg", ".mp4", ".avi", ".mov", ".mkv", ".flv", ".wmv", ".xls", ".xlsx", ".csv",
			".ods", ".tsv", ".pdf", ".docx", ".doc", ".zip", ".rar", ".7z", ".txt", ".mp3" };

	public String determineContentType(String filename) {
		String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

		switch (extension) {
			// Image formats
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "png":
				return "image/png";
			case "bmp":
				return "image/bmp";
			case "gif":
				return "image/gif";
			case "tiff":
			case "tif":
				return "image/tiff";
			case "webp":
				return "image/webp";
			case "ico":
				return "image/x-icon";
			case "svg":
				return "image/svg+xml";

			// Video formats
			case "mp4":
				return "video/mp4";
			case "avi":
				return "video/x-msvideo";
			case "mov":
				return "video/quicktime";
			case "mkv":
				return "video/x-matroska";
			case "flv":
				return "video/x-flv";
			case "wmv":
				return "video/x-ms-wmv";

			// Document formats
			case "pdf":
				return "application/pdf";
			case "doc":
				return "application/msword";
			case "docx":
				return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			case "txt":
				return "text/plain";

			// Spreadsheet formats
			case "xls":
				return "application/vnd.ms-excel";
			case "xlsx":
				return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			case "csv":
				return "text/csv";
			case "ods":
				return "application/vnd.oasis.opendocument.spreadsheet";
			case "tsv":
				return "text/tab-separated-values";

			// Archive formats
			case "zip":
				return "application/zip";
			case "rar":
				return "application/x-rar-compressed";
			case "7z":
				return "application/x-7z-compressed";

			// Audio formats
			case "mp3":
				return "audio/mpeg";

			// Default case for unknown types
			default:
				return "application/octet-stream";
		}
	}

	public Archivo getFileById(String uuid) {
		Optional<Archivo> opt = archivoRepository.findById(uuid);

		Archivo archivo = opt
				.orElseThrow(() -> new ResourceNotFoundException("El archivo no existe en la base de datos"));

		return archivo;
	}

	public Pair<String,Resource> getPlantilla(String uuid) throws FileException, IOException {

		Archivo archivo = getFileById(uuid);

		if(archivo.getPlantillaCertificado() == null){
			throw new FileException("El archivo no es un certificado");
		}

		Path filePath = Paths.get(uploadDirectory).resolve(archivo.getFilename()).normalize();

		log.info("serving file with uuid {}", uuid);

		if (!Files.exists(filePath)) {
			throw new FileException("El archivo no existe en el servidor");
		}

		Resource resource = new PathResource(filePath.toString());
		return new Pair<>(archivo.getNombre(), resource);
	}

	public Pair<String,Resource> getEvidencia(String uuid, Usuario usuario) throws FileException, IOException {

		Archivo archivo = getFileById(uuid);

		if(archivo.getEvidencia() == null){
			throw new FileException("El archivo no es un certificado");
		}

		if(usuario.getAdministrador() == null){
			Long userId = usuario.getId();
			if(archivo.getEvidencia().getSesion().getInstructores().stream().noneMatch(i -> i.getId() == userId)){
				throw new ResourceNotFoundException("Este usuario no es instructor de la sesión");
			}
		}
		
		Path filePath = Paths.get(uploadDirectory).resolve(archivo.getFilename()).normalize();

		log.info("serving file with uuid {}", uuid);

		if (!Files.exists(filePath)) {
			throw new FileException("El archivo no existe en el servidor");
		}

		Resource resource = new PathResource(filePath.toString());
		return new Pair<>(archivo.getNombre(), resource);
	}

	public Pair<String, Resource> getPiezaGrafica(String uuid) throws FileException, IOException {

		Archivo archivo = getFileById(uuid);

		if(archivo.getOfertaFormacion() == null){
			throw new FileException("El archivo no es una pieza grafica");
		}

		Path filePath = Paths.get(uploadDirectory).resolve(archivo.getFilename()).normalize();

		log.info("serving file with uuid {}", uuid);

		if (!Files.exists(filePath)) {
			throw new FileException("El archivo no existe en el servidor");
		}

		Resource resource = new PathResource(filePath.toString());
		return new Pair<>(archivo.getNombre(), resource);
	}

	public Pair<String, Resource> getFile(String uuid) throws FileException, IOException {

		log.info("uuid {}", uuid);

		Archivo archivo = getFileById(uuid);

		Path filePath = Paths.get(uploadDirectory).resolve(archivo.getFilename()).normalize();

		if (!Files.exists(filePath)) {
			throw new FileException("El archivo no existe en el servidor");
		}

		Resource resource = new PathResource(filePath.toString());
		return new Pair<>(archivo.getNombre(), resource);
	}

	public void deleteFile(String uuid) throws IOException {
		Archivo archivo = getFileById(uuid);

		Path filePath = Paths.get(uploadDirectory).resolve(archivo.getFilename()).normalize();

		if (!Files.exists(filePath)) {
			throw new IOException("El archivo " + filePath.toString() + " no existe");
		}

		try {
			Files.delete(filePath);
			archivoRepository.delete(archivo);
		} catch (IOException e) {
			throw new IOException("No se pudo eliminar el archivo: " + archivo.getFilename(), e);
		}
	}

	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public static void validateExtension(String fileName, String[] validExtensions) {
		if (!isValidExtension(fileName, validExtensions)) {
			throw new FileException("La extension " + getFileExtension(fileName) + " del archivo " + fileName
					+ " no está dentro de las permitidas: " + Arrays.toString(validExtensions));
		}
	}

	public static boolean isValidExtension(String fileName, String[] validExtensions) {
		String fileExtension = getFileExtension(fileName);
		return Arrays.stream(validExtensions).anyMatch(ext -> fileExtension.equalsIgnoreCase(ext));
	}

	public Archivo uploadFile(MultipartFile file) throws FileException, IOException {
		log.info("Subiendo archivo: " + file.getOriginalFilename());
		try {
			validateExtension(file.getOriginalFilename(), ALLOWED_EXTENSIONS);
			String fileName = UUID.randomUUID().toString();
			byte[] bytes = file.getBytes();
			String fileOriginalName = file.getOriginalFilename();

			long fileSize = file.getSize();

			if (fileSize > maxFileSize) {
				throw new FileException("El tamaño del archivo es de mas de 10 MB");
			}

			String fileExtension = getFileExtension(fileOriginalName);
			String newFile = fileName + fileExtension;
			File folder = new File(uploadDirectory);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			Path path = Paths.get(uploadDirectory).resolve(newFile).normalize();
			Files.write(path, bytes);
			Archivo archivo = new Archivo();

			archivo.setNombre(file.getOriginalFilename());
			archivo.setExtension(fileExtension);
			archivo.setUuid(fileName);

			log.info("newFile {}", path.toString());
			return archivoRepository.save(archivo);
		} catch (Exception e) {
			throw new FileException(e.getMessage(), e);
		}
	}

}

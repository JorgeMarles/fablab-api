package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.FileException;

@Service
public class FileService {

	Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private final String uploadDirectory = "uploads";
	
	public Resource getFile(String filename) throws IOException {
		Path filePath = Paths.get(uploadDirectory).resolve(filename).normalize();
        
		if(!Files.exists(filePath)) {
			throw new IOException("El archivo no existe");
		}
		
        Resource resource = new PathResource(filePath.toString());
        return resource;
	}
	
	public void deleteFile(String filename) throws IOException {
		Path filePath = Paths.get(uploadDirectory).resolve(filename).normalize();
        
		if(!Files.exists(filePath)) {
			throw new IOException("El archivo no existe");
		}
        
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new IOException("No se pudo eliminar el archivo: " + filename, e);
        }
	}

	public String uploadFile(MultipartFile file) throws Exception {
		logger.info("Subiendo archivo: " + file.getOriginalFilename());
		return "hola";
		/*/
		try {
			String fileName = UUID.randomUUID().toString();
			byte[] bytes = file.getBytes();
			String fileOriginalName = file.getOriginalFilename();

			long fileSize = file.getSize();
			long maxFileSize = 10 * 1024 * 1024;

			if (fileSize > maxFileSize) {
				throw new FileException("El tamaño del archivo es de mas de 10 MB");
			}

			if (fileOriginalName.endsWith(".jpg") || fileOriginalName.endsWith(".png") || fileOriginalName.endsWith(".jpeg")
					|| fileOriginalName.endsWith(".bmp") || fileOriginalName.endsWith(".gif")
					|| fileOriginalName.endsWith(".tiff") || fileOriginalName.endsWith(".tif")
					|| fileOriginalName.endsWith(".webp") || fileOriginalName.endsWith(".ico")
					|| fileOriginalName.endsWith(".svg") || fileOriginalName.endsWith(".mp4")
					|| fileOriginalName.endsWith(".avi") || fileOriginalName.endsWith(".mov")
					|| fileOriginalName.endsWith(".mkv") || fileOriginalName.endsWith(".flv")
					|| fileOriginalName.endsWith(".wmv") || fileOriginalName.endsWith(".xls")
					|| fileOriginalName.endsWith(".xlsx") || fileOriginalName.endsWith(".csv")
					|| fileOriginalName.endsWith(".ods") || fileOriginalName.endsWith(".tsv")||fileOriginalName.endsWith(".zip")) {
					String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
					String newFile = fileName+fileExtension;
					File folder =  new File(uploadDirectory);
					
					if(!folder.exists()) {
						folder.mkdirs();
					}
					
					Path path = Paths.get(uploadDirectory).resolve(newFile).normalize();
					Files.write(path, bytes);
					return path.getFileName().toString().replace("\\", "/");
			} else {
				throw new FileException("Ese Tipo de archivo no esta permitido, y este es el nombre:" + fileOriginalName + " y pesa: " +fileSize);
			}
		} catch (Exception e) {
			throw new FileException(e.getMessage());
		}
			*/
	}

}

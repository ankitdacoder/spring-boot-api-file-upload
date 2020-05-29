package com.developer.idea.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.developer.idea.config.FileStorageProperties;
import com.developer.idea.exception.FileStorageException;

@Service
public class FileStorageService {
	 private final Path fileStorageLocation;
	 @Autowired
	    public FileStorageService(FileStorageProperties fileStorageProperties) {
	        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
	            .toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
              throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }
	 
	  public String storeFile(MultipartFile file) {
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            Path targetLocation = this.fileStorageLocation.resolve("report.csv");
	           
//	            Files.walkFileTree(targetLocation, new SimpleFileVisitor<Path>() {
//	     		   @Override
//	     		   public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
//	     		       Files.delete(file); // this will work because it's always a File
//	     		       return FileVisitResult.CONTINUE;
//	     		   }
//
//	     		 
//	     		});
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return fileName;
	        } catch (Exception ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }
}

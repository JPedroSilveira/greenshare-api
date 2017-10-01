package com.seedshare.controller.photo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author joao.silva
 */
public interface ImageUploadController{
		
	ResponseEntity<?> handleFileUpload(MultipartFile file);
	
}

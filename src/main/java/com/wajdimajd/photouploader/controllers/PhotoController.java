package com.wajdimajd.photouploader.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

	@PostMapping("/upload")
	public ResponseEntity<String> photoUpload(@RequestParam("image") MultipartFile image) throws ResponseStatusException
    {
		Path path = Paths.get("images", 
			String.format(
				"%s.%s", 
				UUID.randomUUID().toString(), 
				StringUtils.getFilenameExtension(image.getOriginalFilename())
			)
		);

		try {
			Files.write(path, image.getBytes(), StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write file");
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}

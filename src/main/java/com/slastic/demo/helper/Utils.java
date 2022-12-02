package com.slastic.demo.helper;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

import org.slf4j.Logger;

public class Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static String loadFileAsString (String path) {
		
		try {
			final File resource = new ClassPathResource(path).getFile();
			
			return new String(Files.readAllBytes(resource.toPath()));
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}

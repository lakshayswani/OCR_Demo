package com.lakshayswani.service;

import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;

public interface TikaService {

	public Response parseFile(MultipartFile inputFile);
	
	public Response parseImagePdf(MultipartFile inputFile);
	
}

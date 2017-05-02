package com.lakshayswani.service;

import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;

public interface AsposeService {

	public Response parseImage(MultipartFile inputFile);

}

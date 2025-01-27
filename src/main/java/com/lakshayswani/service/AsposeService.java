package com.lakshayswani.service;

import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;

// TODO: Auto-generated Javadoc
/**
 * The Interface AsposeService.
 */
public interface AsposeService {

	/**
	 * Parses the image.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response
	 */
	public Response parseImage(MultipartFile inputFile);

}

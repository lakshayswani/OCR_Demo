package com.lakshayswani.service;

import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;

// TODO: Auto-generated Javadoc
/**
 * The Interface ApriseService.
 */
public interface ApriseService {

	/**
	 * Parses the image.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response
	 */
	public Response parseImage(MultipartFile inputFile);

}

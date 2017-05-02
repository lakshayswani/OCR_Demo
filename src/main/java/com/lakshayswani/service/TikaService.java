package com.lakshayswani.service;

import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;

// TODO: Auto-generated Javadoc
/**
 * The Interface TikaService.
 */
public interface TikaService {

	/**
	 * Parses the file.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response
	 */
	public Response parseFile(MultipartFile inputFile);

	/**
	 * Parses the image pdf.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response
	 */
	public Response parseImagePdf(MultipartFile inputFile);

	/**
	 * Parses the image.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response
	 */
	public Response parseImage(MultipartFile inputFile);

}

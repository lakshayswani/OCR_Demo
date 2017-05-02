package com.lakshayswani.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lakshayswani.response.Response;
import com.lakshayswani.service.TikaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class TikaController.
 */
@RestController
@Api("OCR Api")
@RequestMapping(value = { "/api" })
public class TikaController {

	/** The tika service. */
	@Autowired
	TikaService tikaService;

	/**
	 * Parses the document.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response entity
	 * @throws Exception
	 *             the exception
	 */
	@ApiOperation(value = "/tika/docOcr", notes = "Tika OCR parser for PDF/DOC")
	@RequestMapping(value = "/tika/docOcr", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parseDocument(@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {

		Response response = tikaService.parseFile(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}

	/**
	 * Parses the PDF document.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response entity
	 * @throws Exception
	 *             the exception
	 */
	@ApiOperation(value = "/tika/pdfImageOcr", notes = "Tika OCR parser for PDF containing images")
	@RequestMapping(value = "/tika/pdfImageOcr", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parsePDFDocument(@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {

		Response response = tikaService.parseImagePdf(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}

	/**
	 * Parses the image document.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response entity
	 * @throws Exception
	 *             the exception
	 */
	@ApiOperation(value = "/tika/imageOcr", notes = "Tika OCR parser for Images")
	@RequestMapping(value = "/tika/imageOcr", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parseImageDocument(@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {

		Response response = tikaService.parseImage(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}

}

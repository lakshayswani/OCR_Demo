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
import com.lakshayswani.service.ApriseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class ApriseController.
 */
@RestController
@Api("OCR Api")
@RequestMapping(value = { "/api" })
public class ApriseController {

	/** The aprise service. */
	@Autowired
	ApriseService apriseService;

	/**
	 * Parses the document.
	 *
	 * @param inputFile
	 *            the input file
	 * @return the response entity
	 * @throws Exception
	 *             the exception
	 */
	@ApiOperation(value = "/aprise/ocr", notes = "Aprise OCR parser for Images")
	@RequestMapping(value = "/aprise/ocr", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parseDocument(@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {

		Response response = apriseService.parseImage(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}

}

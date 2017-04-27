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

@RestController
@Api("OCR Api")
@RequestMapping(value = { "/api"})
public class TikaController {

	@Autowired
	TikaService tikaService;

	@ApiOperation(value = "/tika/docOcr", notes = "Tika OCR parse")
	@RequestMapping(value = "/tika/docOcr", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parseDocument(
			@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {
		
		Response response = tikaService.parseFile(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}
	
	@ApiOperation(value = "/tika/pdfImageOcr", notes = "Tika OCR parse")
	@RequestMapping(value = "/tika/pdfImageOcr", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parsePDFDocument(
			@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {
		
		Response response = tikaService.parseImagePdf(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}
	
	@ApiOperation(value = "/tika/imageOcr", notes = "Tika OCR parse")
	@RequestMapping(value = "/tika/imageOcr", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response> parseImageDocument(
			@RequestPart(required = true) MultipartFile inputFile)
			throws Exception {
		
		Response response = tikaService.parseImage(inputFile);
		return new ResponseEntity<>(response, response.getHttpStatus());

	}

}

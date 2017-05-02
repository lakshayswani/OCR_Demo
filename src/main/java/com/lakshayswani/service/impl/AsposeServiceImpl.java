package com.lakshayswani.service.impl;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.ocr.ImageStream;
import com.aspose.ocr.OcrEngine;
import com.lakshayswani.response.Response;
import com.lakshayswani.service.AsposeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AsposeServiceImpl.
 */
@Service
public class AsposeServiceImpl implements AsposeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lakshayswani.service.AsposeService#parseImage(org.springframework.web
	 * .multipart.MultipartFile)
	 */
	@Override
	public Response parseImage(MultipartFile inputFile) {

		Response response = null;
		OcrEngine ocr = new OcrEngine();
		try {
			ocr.setImage(ImageStream.fromStream(inputFile.getInputStream(), 0));
			if (ocr.process()) {
				response = new Response(null, Arrays.asList(ocr.getText().toString()), HttpStatus.OK, "Completed");
			} else {
				response = new Response(HttpStatus.BAD_REQUEST, "Unable to process");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e1.getMessage());
		}

		return response;
	}

}

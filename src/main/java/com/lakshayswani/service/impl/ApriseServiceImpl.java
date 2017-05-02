package com.lakshayswani.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asprise.ocr.Ocr;
import com.lakshayswani.response.Response;
import com.lakshayswani.service.ApriseService;

// TODO: Auto-generated Javadoc
/**
 * The Class ApriseServiceImpl.
 */
@Service
public class ApriseServiceImpl implements ApriseService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lakshayswani.service.ApriseService#parseImage(org.springframework.web
	 * .multipart.MultipartFile)
	 */
	@SuppressWarnings("null")
	@Override
	public Response parseImage(MultipartFile inputFile) {
		Response response = null;
		Ocr ocr = null;
		File file = new File(inputFile.getOriginalFilename());
		try {
			FileUtils.copyInputStreamToFile(inputFile.getInputStream(), file);
			ocr = new Ocr();
			ocr.startEngine("eng", Ocr.SPEED_FASTEST);
			String result = ocr.recognize(new File[] { file }, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
			response = new Response(null, Arrays.asList(result), HttpStatus.OK, "Completed");
			ocr.stopEngine();
		} catch (IOException e) {
			ocr.stopEngine();
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return response;
	}

}

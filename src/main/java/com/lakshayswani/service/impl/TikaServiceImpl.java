package com.lakshayswani.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.bytedeco.javacpp.BytePointer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;

import com.lakshayswani.response.Response;
import com.lakshayswani.service.TikaService;

// TODO: Auto-generated Javadoc
/**
 * The Class TikaServiceImpl.
 */
@Service
public class TikaServiceImpl implements TikaService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lakshayswani.service.TikaService#parseFile(org.springframework.web.
	 * multipart.MultipartFile)
	 */
	@Override
	public Response parseFile(MultipartFile inputFile) {
		Response response;
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
		TesseractOCRConfig config = new TesseractOCRConfig();
		config.setTessdataPath("D:\\Workspace\\Personal\\OCR\\tessdata");
		PDFParserConfig pdfConfig = new PDFParserConfig();
		pdfConfig.setExtractInlineImages(true);

		ParseContext parseContext = new ParseContext();
		parseContext.set(TesseractOCRConfig.class, config);
		parseContext.set(PDFParserConfig.class, pdfConfig);
		parseContext.set(Parser.class, parser);

		try {
			Metadata metadata = new Metadata();
			parser.parse(inputFile.getInputStream(), handler, metadata, parseContext);
			response = new Response(metadata.toString(), Arrays.asList(handler.toString()), HttpStatus.OK, "Completed");
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lakshayswani.service.TikaService#parseImagePdf(org.springframework.
	 * web.multipart.MultipartFile)
	 */
	@Override
	public Response parseImagePdf(MultipartFile inputFile) {
		Response response;
		BytePointer outText = null;
		PIX image = null;
		TessBaseAPI api = null;
		try {
			List<String> content = new ArrayList<>();
			PDDocument document = PDDocument.load(inputFile.getInputStream());
			PDFRenderer renderer = new PDFRenderer(document);
			FileOutputStream fos = new FileOutputStream(inputFile.getName() + ".txt");
			int pages = document.getDocumentCatalog().getPages().getCount();
			for (int i = 0; i < pages; i++) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(renderer.renderImage(i), "jpeg", baos);
				try (OutputStream outputStream = new FileOutputStream("abc.jpeg")) {
					baos.writeTo(outputStream);
				}
				api = new TessBaseAPI();
				if (api.Init("D:\\Workspace\\Personal\\OCR\\tessdata", "ENG") != 0) {
					response = new Response(HttpStatus.BAD_REQUEST, "Could not initialize tesseract.");
				} else {
					image = pixReadMem(baos.toByteArray(), baos.size());
					api.SetImage(image);
					outText = api.GetUTF8Text();
					fos.write(outText.getStringBytes());
					content.add(outText.getString());
				}

			}
			fos.close();
			response = new Response(null, content, HttpStatus.OK, "Completed");

		} catch (IOException e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lakshayswani.service.TikaService#parseImage(org.springframework.web.
	 * multipart.MultipartFile)
	 */
	@Override
	public Response parseImage(MultipartFile inputFile) {
		Response response;
		BytePointer outText = null;
		PIX image = null;
		TessBaseAPI api = null;
		try {
			api = new TessBaseAPI();
			if ((api.Init("D:\\Workspace\\Personal\\OCR\\tessdata", "ENG") != 0)) {
				response = new Response(HttpStatus.BAD_REQUEST, "Could not initialize tesseract.");
			} else {
				image = pixReadMem(inputFile.getBytes(), inputFile.getSize());
				api.SetImage(image);
				outText = api.GetUTF8Text();
				FileOutputStream fos = new FileOutputStream("xyz.txt");
				fos.write(outText.getStringBytes());
				fos.close();
				response = new Response(null, Arrays.asList(outText.getString()), HttpStatus.OK, "Completed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		} finally {
			api.End();
			outText.deallocate();
			pixDestroy(image);
		}
		return response;
	}

}

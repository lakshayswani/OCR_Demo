package com.lakshayswani.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import static org.bytedeco.javacpp.lept.*;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
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
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.lakshayswani.response.Response;
import com.lakshayswani.service.TikaService;

@Service
public class TikaServiceImpl implements TikaService{

	@Override
	public Response parseFile(MultipartFile inputFile) {
		Response response;
		Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
        TesseractOCRConfig config = new TesseractOCRConfig();
        PDFParserConfig pdfConfig = new PDFParserConfig();
        pdfConfig.setExtractInlineImages(true);

        ParseContext parseContext = new ParseContext();
        parseContext.set(TesseractOCRConfig.class, config);
        parseContext.set(PDFParserConfig.class, pdfConfig);
        parseContext.set(Parser.class, parser);

		try {
			Metadata metadata = new Metadata();
			parser.parse(inputFile.getInputStream(), handler, metadata, parseContext);
			response = new Response(metadata.toString(), handler.toString(), HttpStatus.OK, "Completed");
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return response;
	}

	@Override
	public Response parseImagePdf(MultipartFile inputFile) {
		Response response;
		Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
        TesseractOCRConfig config = new TesseractOCRConfig();
        PDFParserConfig pdfConfig = new PDFParserConfig();
        pdfConfig.setExtractInlineImages(true);

        ParseContext parseContext = new ParseContext();
        parseContext.set(TesseractOCRConfig.class, config);
        parseContext.set(PDFParserConfig.class, pdfConfig);
        parseContext.set(Parser.class, parser);
        Metadata metadata = new Metadata();
        StringBuilder stringMetadata = new StringBuilder();
        StringBuilder stringContent = new StringBuilder();
		try {
			PDDocument document = PDDocument.load(inputFile.getInputStream());
		    PDFRenderer renderer = new PDFRenderer(document);
		    int pages = document.getDocumentCatalog().getPages().getCount();
		    for(int i = 0; i<pages; i++)
		    {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	ImageIO.write(renderer.renderImage(i), "png", baos);
            	InputStream is = new ByteArrayInputStream(baos.toByteArray());
    			parser.parse(is, handler, metadata, parseContext);
    			stringMetadata.append(metadata.toString());
    			stringContent.append(handler.toString());
		    }
            response = new Response(stringMetadata.toString(), stringContent.toString(), HttpStatus.OK, "Completed");
            
            
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		return response;
	}

	@Override
	public Response parseImage(MultipartFile inputFile) {
		Response response;
        BytePointer outText = null;
        PIX image = null;
        TessBaseAPI api = new TessBaseAPI();
        if (api.Init(null, "eng") != 0) {
			response = new Response(HttpStatus.BAD_REQUEST, "Could not initialize tesseract.");
            return response;
        }
        try {
			image = pixRead(new BytePointer(inputFile.getBytes()));
	        api.SetImage(image);
	        outText = api.GetUTF8Text();
            response = new Response(null, outText.getString(), HttpStatus.OK, "Completed");
		} catch (IOException e) {
			e.printStackTrace();
			response = new Response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
        finally
        {
        	api.End();
            outText.deallocate();
            pixDestroy(image);
        }
		return null;
	}

}

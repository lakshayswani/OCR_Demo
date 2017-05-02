package com.lakshayswani.response;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * The Class Response.
 */
public class Response {

	/** The metadata. */
	private String metadata;

	/** The content. */
	private List<String> content;

	/** The http status. */
	private HttpStatus httpStatus;

	/** The message. */
	private String message;

	/**
	 * Gets the metadata.
	 *
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * Sets the metadata.
	 *
	 * @param metadata
	 *            the new metadata
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public List<String> getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(List<String> content) {
		this.content = content;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus
	 *            the new http status
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param metadata
	 *            the metadata
	 * @param content
	 *            the content
	 * @param httpStatus
	 *            the http status
	 * @param message
	 *            the message
	 */
	public Response(String metadata, List<String> content, HttpStatus httpStatus, String message) {
		this.metadata = metadata;
		this.content = content;
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param httpStatus
	 *            the http status
	 * @param message
	 *            the message
	 */
	public Response(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

}

package com.se.hcmut_sso.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
@Getter
public class ServerException extends RuntimeException {
	private final HttpStatusCode httpStatusCode;
	public ServerException(String message, HttpStatusCode httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}
}

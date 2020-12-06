package domain;

import exception.InvalidResponseBodyException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
	private static final String DEFAULT_RESPONSE = "Hello World";
	private static final String DEFAULT_VERSION = "HTTP/1.1";
	private DataOutputStream dos;
	private HttpStatus httpStatus;
	private ContentType contentType;
	private List<HttpHeader> headers;

	public HttpResponse(OutputStream out) {
		this.dos = new DataOutputStream(out);
		this.httpStatus = HttpStatus.OK;
		this.headers = new ArrayList<>();
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public void forward(String path) {
		setHttpStatus(HttpStatus.OK);
		this.forwardBody(responseBody(path));
	}

	public void forwardBody(byte[] body) {
		try {
			if (body.length == 0) {
				body = DEFAULT_RESPONSE.getBytes();
			}
			addHeader("Content-Type", contentType != null ? contentType.getType() : "");
			addHeader("Content-Length", String.valueOf(body.length));
			dos.writeBytes(String.format("%s %s \r\n", DEFAULT_VERSION, httpStatus.toString()));
			for (HttpHeader header : headers) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	public void sendRedirect(String location) {
		try {
			addHeader("Location", location);
			dos.writeBytes(String.format("%s %s \r\n", DEFAULT_VERSION, httpStatus.toString()));
			for (HttpHeader header : headers) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	private byte[] responseBody(String requestPath) {
		try {
			if ("/".equals(requestPath) || requestPath == null) {
				return DEFAULT_RESPONSE.getBytes();
			}
			return FileIoUtils.loadFileFromClasspath(RequestPathUtils.getResourcePath(requestPath));
		} catch (IOException | URISyntaxException ex) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	public void addHeader(String key, String value) {
		headers.add(new HttpHeader(key, value));
	}
}

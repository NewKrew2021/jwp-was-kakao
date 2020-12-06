package domain;

import exception.InvalidResponseBodyException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
	private static final String DEFAULT_RESPONSE = "Hello World";
	private static final String DEFAULT_VERSION = "HTTP/1.1";
	private DataOutputStream dos;
	private HttpStatus httpStatus;
	private ContentType contentType;

	public HttpResponse(OutputStream out) {
		this.dos = new DataOutputStream(out);
		this.httpStatus = HttpStatus.OK;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public void forward(HttpRequest request) {
		setHttpStatus(HttpStatus.OK);
		setContentType(request.getContentType());
		this.forwardBody(responseBody(request.getPath()));
	}

	public void forwardBody(byte[] body) {
		try {
			if (body.length == 0) {
				body = DEFAULT_RESPONSE.getBytes();
			}
			dos.writeBytes(String.format("%s %s \r\n", DEFAULT_VERSION, httpStatus.toString()));
			for (HttpHeader header : response200Header(body.length, contentType)) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	public void sendRedirect(String location, boolean loginSuccess) {
		try {
			dos.writeBytes(String.format("%s %s \r\n", DEFAULT_VERSION, httpStatus.toString()));
			for (HttpHeader header : response302Header(location, loginSuccess)) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	private List<HttpHeader> response200Header(int bodyLength, ContentType contentType) {
		return Arrays.asList(
				new HttpHeader("Content-Type", contentType.getType()),
				new HttpHeader("Content-Length", String.valueOf(bodyLength))
		);
	}

	private List<HttpHeader> response302Header(String location, boolean loginSuccess) {
		return Arrays.asList(
				new HttpHeader("Location", location),
				new HttpHeader("Set-Cookie", (loginSuccess ? "logined=true;" : "logined=false;"))
		);
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
}

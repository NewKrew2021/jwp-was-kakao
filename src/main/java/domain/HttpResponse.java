package domain;

import exception.InvalidResponseBodyException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
	private static final String DEFAULT_RESPONSE = "Hello World";
	private DataOutputStream dos;
	private String httpVersion;
	private ContentType contentType;
	private String path;

	public HttpResponse(DataOutputStream dos, HttpRequest httpRequest) {
		this.dos = dos;
		this.httpVersion = getHttpVersion(httpRequest.getHttpVersion());
		this.contentType = httpRequest.getContentType();
		this.path = httpRequest.getPath();
	}

	private String getHttpVersion(String httpVersion) {
		return httpVersion != null && !httpVersion.isEmpty() ? httpVersion : "HTTP/1.1";
	}

	public void response200OK() {
		this.response200OK(getResponseBody(path));
	}

	public void response200OK(byte[] body) {
		try {
			if (body.length == 0) {
				body = DEFAULT_RESPONSE.getBytes();
			}
			dos.writeBytes(String.format("%s %s \r\n", httpVersion, HttpStatus.OK.toString()));
			for (HttpHeader header : make200Header(body.length)) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	public void response302Redirect(String location, boolean loginSuccess) {
		try {
			dos.writeBytes(String.format("%s %s \r\n", httpVersion, HttpStatus.FOUND.toString()));
			for (HttpHeader header : make302Header(location, loginSuccess)) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	private List<HttpHeader> make200Header(int bodyLength) {
		HttpHeader contentTypeHeader = new HttpHeader("Content-Type", contentType.getType());
		HttpHeader contentLength = new HttpHeader("Content-Length", String.valueOf(bodyLength));
		return Arrays.asList(contentTypeHeader, contentLength);
	}

	private List<HttpHeader> make302Header(String location, boolean loginSuccess) {
		HttpHeader locationHeader = new HttpHeader("Location", location);
		HttpHeader loginCookie = new HttpHeader("Set-Cookie", (loginSuccess ? "logined=true;" : "logined=false;"));
		return Arrays.asList(locationHeader, loginCookie);
	}

	private byte[] getResponseBody(String requestPath) {
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

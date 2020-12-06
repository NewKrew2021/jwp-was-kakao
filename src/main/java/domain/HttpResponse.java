package domain;

import exception.InvalidResponseBodyException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
	private static final String DEFAULT_RESPONSE = "Hello World";
	private DataOutputStream dos;
	private String httpVersion;
	private ContentType contentType;
	private String path;
	private List<HttpHeader> headers;

	public HttpResponse(DataOutputStream dos, HttpRequest httpRequest) {
		this.dos = dos;
		this.httpVersion = getHttpVersion(httpRequest.getHttpVersion());
		this.contentType = httpRequest.getContentType();
		this.path = httpRequest.getPath();
		this.headers = new ArrayList<>();
	}

	private String getHttpVersion(String httpVersion) {
		return httpVersion != null && !httpVersion.isEmpty() ? httpVersion : "HTTP/1.1";
	}

	public void forward() {
		this.forwardBody(responseBody(path));
	}

	public void forwardBody(byte[] body) {
		try {
			if (body.length == 0) {
				body = DEFAULT_RESPONSE.getBytes();
			}
			dos.writeBytes(String.format("%s %s \r\n", httpVersion, HttpStatus.OK.toString()));
			for (HttpHeader header : response200Header(body.length)) {
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
			dos.writeBytes(String.format("%s %s \r\n", httpVersion, HttpStatus.FOUND.toString()));
			for (HttpHeader header : response302Header(location, loginSuccess)) {
				dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			throw new InvalidResponseBodyException("응답 데이터 오류입니다.");
		}
	}

	private List<HttpHeader> response200Header(int bodyLength) {
		addHeader("Content-Type", contentType.getType());
		addHeader("Content-Length", String.valueOf(bodyLength));
		return headers;
	}

	private List<HttpHeader> response302Header(String location, boolean loginSuccess) {
		addHeader("Location", location);
		addHeader("Set-Cookie", (loginSuccess ? "logined=true;" : "logined=false;"));
		return headers;
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

	private void addHeader(String key, String value) {
		headers.add(new HttpHeader(key, value));
	}
}

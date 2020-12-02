package domain;

import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
	private static final String DEFAULT_RESPONSE = "Hello World";
	private DataOutputStream dos;

	public HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}

	public void response200Header(HttpRequest httpRequest) {
		this.response200Header(getBody(httpRequest.getPath()), httpRequest);
	}
	public void response200Header(byte[] body, HttpRequest httpRequest) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			if (httpRequest.getMimeType() != null) {
				dos.writeBytes("Content-Type: " + httpRequest.getMimeType().getType() + "\r\n");
			}
			dos.writeBytes("Content-Length: " + body.length + "\r\n");
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			throw new RuntimeException("응답 메시지 오류입니다.");
		}
	}

	public void response302Header(String location, String cookie) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: " + location + "\r\n");
			if (!cookie.isEmpty()) {
				dos.writeBytes(cookie + " Path=/" + "\r\n");
			}
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			throw new RuntimeException("응답 메시지 오류입니다.");
		}
	}

	public byte[] getBody(String requestPath) {
		try {
			if ("/".equals(requestPath)) {
				return DEFAULT_RESPONSE.getBytes();
			}
			return FileIoUtils.loadFileFromClasspath(RequestPathUtils.getResourcePath(requestPath));
		} catch (IOException | URISyntaxException ex) {
			throw new RuntimeException("응답 메시지 오류입니다.");
		}
	}
}

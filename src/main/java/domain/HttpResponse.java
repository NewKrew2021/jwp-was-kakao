package domain;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
	private DataOutputStream dos;

	public HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}

	public void response200Header(byte[] body, MimeType mimeType) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			if (mimeType != null) {
				dos.writeBytes("Content-Type: " + mimeType.getType() + "\r\n");
			}
			dos.writeBytes("Content-Length: " + body.length + "\r\n");
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			throw new RuntimeException();
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
			throw new RuntimeException();
		}
	}
}

package webserver.domain;

import webserver.exceptions.AlreadySentException;
import webserver.exceptions.RequiredHeaderNotFoundException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private final DataOutputStream dos;
    private final HttpHeaders headers;
    private boolean isSent;

    public HttpResponse(OutputStream out) throws IOException {
        dos = new DataOutputStream(out);
        headers = new HttpHeaders();
        isSent = false;
    }

    public void send(HttpStatusCode code) {
        send(code, "");
    }

    public void send(HttpStatusCode code, String body) {
        send(code, body.getBytes(StandardCharsets.UTF_8));
    }

    public void send(HttpStatusCode code, byte[] body) {
        if(isSent) {
            throw new AlreadySentException("이미 한번 이상 전송된 response입니다");
        }
        if (body == null) {
            body = new byte[]{};
        }
        if (body.length > 0) {
            headers.add(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length));
        }
        try {
            sendHeaders(code);
            dos.writeBytes("\r\n");
            dos.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isSent = true;
    }

    public boolean isSent() {
        return isSent;
    }

    private void sendHeaders(HttpStatusCode code) throws IOException {
        validateRequiredHeaders(code);
        dos.writeBytes(String.format("HTTP/1.1 %d %s\r\n", code.getCode(), code.getMessage()));
        printHeader();
    }

    private void validateRequiredHeaders(HttpStatusCode code) {
        for (HttpHeader header : code.getRequiredHeaders()) {
            if (!headers.contain(header)) {
                throw new RequiredHeaderNotFoundException(code.getCode() + " HTTP 코드에 따른 필수 응답 헤더인 " + header.getMessage() + "가 없습니다");
            }
        }
    }

    private void printHeader() throws IOException {
        for (String key : headers.getHeaders().keySet()) {
            String value = headers.getHeaders().get(key);
            dos.writeBytes(String.format("%s: %s\r\n", key, value));
        }
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

}

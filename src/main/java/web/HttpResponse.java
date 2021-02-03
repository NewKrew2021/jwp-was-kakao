package web;

import org.springframework.http.HttpStatus;
import utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final String HTTP_1_1 = "HTTP/1.1 ";

    private final HttpHeaders httpHeaders;
    private final HttpStatus httpStatus;
    private HttpBody httpBody;

    private HttpResponse(HttpStatus httpStatus, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public static HttpResponse of(HttpStatus httpStatus) {
        return new HttpResponse(httpStatus, HttpHeaders.empty(), HttpBody.empty());
    }

    public void addHeader(String key, String value) {
        httpHeaders.add(key, value);
    }

    public void setBody(byte[] body) {
        httpBody = new HttpBody(body);
    }

    public void setBody(String body) {
        setBody(body.getBytes());
    }

    public byte[] asBytes() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            writeStartLine(outputStream);
            writeHeader(outputStream);
            writeBody(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void writeStartLine(ByteArrayOutputStream outputStream) throws IOException {
        outputStream.write(HTTP_1_1.getBytes());
        outputStream.write(String.valueOf(httpStatus.value()).getBytes());
        outputStream.write(' ');
        outputStream.write(httpStatus.getReasonPhrase().getBytes());
        outputStream.write(IOUtils.CRLF.getBytes());
    }

    private void writeHeader(ByteArrayOutputStream outputStream) throws IOException {
        if (!httpHeaders.isEmpty()) {
            outputStream.write(httpHeaders.toString().getBytes());
        }
    }

    private void writeBody(ByteArrayOutputStream outputStream) throws IOException {
        if (!httpBody.equals(HttpBody.empty())) {
            outputStream.write(IOUtils.CRLF.getBytes());
            outputStream.write(httpBody.getBody());
            outputStream.write(IOUtils.CRLF.getBytes());
        }
    }
}

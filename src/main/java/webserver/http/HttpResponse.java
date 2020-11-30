package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;

    private List<HttpHeader> headers;
    private String cookie;
    private byte[] body;
    private HttpStatus status;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.headers = new ArrayList<>();
    }

    public void setBody(byte[] body) {
        this.body = body;
        addHeader("Content-Length", String.valueOf(body.length));
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status == null ? HttpStatus.x200_OK : status;
    }

    public String getStatusLine() {
        return "HTTP/1.1 " + getStatus();
    }

    public List<HttpHeader> getHeaders() {
        List<HttpHeader> allHeaders = new ArrayList<>();
        allHeaders.addAll(Collections.unmodifiableList(headers));
        if( cookie != null ) allHeaders.add(new HttpHeader("Set-Cookie", cookie));

        return allHeaders;
    }

    public void addHeader(HttpHeader header){
        headers.add(header);
    }

    public void addHeader(String key, String value) {
        headers.add(new HttpHeader(key, value));
    }

    public void setCookie(String setCookieHeaderValue) {
        cookie = setCookieHeaderValue;
    }

    public void setContentType(String contentType) {
        headers.add(new HttpHeader("Content-Type", contentType));
    }

    private void writeStatusLine() throws IOException {
        dos.writeBytes(getStatusLine() + " \r\n");
    }

    private void writeHeader() throws IOException {
        for (HttpHeader header : getHeaders()) {
            dos.writeBytes(header.toString() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody() throws IOException {
        if (body != null && body.length > 0)
            dos.write(body, 0, body.length);
    }

    private void flush() throws IOException {
        dos.flush();
    }

    public void sendRedirect(String location, String contentType, List<HttpHeader> headers){
        setStatus(HttpStatus.x302_Found);
        setContentType(contentType);
        addHeader("Location", location);
        headers.stream().forEach(this::addHeader);
        send();
    }

    public void sendRedirect(String location) {
        sendRedirect(location, "text/html", new ArrayList<>());
    }

    public void send() {
        try {
            writeStatusLine();
            writeHeader();
            writeBody();
            flush();
        } catch (IOException e) {
            throw new RuntimeException("Http 응답메세지를 보내는과정에 문제가 발생했습니다", e);
        } finally {
            logger.debug("---- response-status-line ---");
            logger.debug(getStatusLine());
            logger.debug("---- response-header ----");
            getHeaders().forEach(it -> logger.debug(it.toString()));
        }
    }
}

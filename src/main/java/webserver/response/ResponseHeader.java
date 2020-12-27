package webserver.response;

import org.springframework.http.HttpStatus;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.request.Protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static webserver.request.HttpRequest.SESSION_ID;

public class ResponseHeader {

    private Protocol protocol;
    private List<String> headers;
    private HttpStatus status;

    private ResponseHeader(Protocol protocol, HttpStatus status, List<String> headers) {
        this.protocol = protocol;
        this.status = status;
        this.headers = headers;
    }

    public static ResponseHeader ok(HttpRequest request) {
        ResponseHeader responseHeader = new ResponseHeader(request.getProtocol(), HttpStatus.OK, new ArrayList<>());
        responseHeader.setCookie(new Cookie(SESSION_ID, request.getSession().getId(), "/"));
        return responseHeader;
    }

    public static ResponseHeader error() {
        return new ResponseHeader(Protocol.HTTP, HttpStatus.INTERNAL_SERVER_ERROR, Collections.emptyList());
    }

    public void setContentLength(int contentLength) {
        headers.add(String.format("Content-Length: %d\r\n", contentLength));
    }

    public void setContentType(String contentType) {
        headers.add(String.format("Content-Type: %s\r\n", contentType));
    }

    public void setLocation(String location) {
        headers.add(String.format("Location: %s\r\n", location));
    }

    public void setCookie(Cookie cookie) {
        headers.add(String.format("Set-Cookie: %s; Path=%s\r\n", cookie.getContent(), cookie.getPath()));
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return String.join("",
                String.format("%s %d %s \r\n", protocol.getMessage(), status.value(), status.getReasonPhrase()),
                String.join("", headers),
                "\r\n"
        );
    }
}

package webserver.response;

import webserver.request.ContentType;
import webserver.request.Protocol;
import webserver.request.Status;

import java.util.Optional;

public class ResponseHeader {

    private Protocol protocol;
    private Status status;
    private String cookie;
    private String cookiePath;
    private String location;
    private ContentType contentType;
    private Integer contentLength;

    private ResponseHeader() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ResponseHeader header;

        public Builder() {
            this.header = new ResponseHeader();
        }

        public Builder protocol(Protocol protocol) {
            header.setProtocol(protocol);
            return this;
        }

        public Builder status(Status status) {
            header.setStatus(status);
            return this;
        }

        public ResponseHeader build() {
            return header;
        }

        public Builder cookie(String cookie, String cookiePath) {
            header.setCookie(cookie);
            header.setCookiePath(cookiePath);
            return this;
        }

        public Builder location(String location) {
            header.setLocation(location);
            return this;
        }

        public Builder contentType(ContentType contentType) {
            header.setContentType(contentType);
            return this;
        }

        public Builder contentLength(int contentLength) {
            header.setContentLength(contentLength);
            return this;
        }
    }

    private void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    private void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    private void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    private void setCookie(String cookie) {
        this.cookie = cookie;
    }

    private void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    private void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.join("",
                Optional.ofNullable(protocol).map(protocol -> String.format("%s %s \r\n", protocol.getMessage(), status.getMessage())).orElse(""),
                Optional.ofNullable(cookie).map(cookie -> String.format("Set-Cookie: %s; Path=%s\r\n", cookie, cookiePath)).orElse(""),
                Optional.ofNullable(location).map(location -> String.format("Location: %s\r\n", location)).orElse(""),
                Optional.ofNullable(contentType).map(contentType -> String.format("Content-Type: %s\r\n", contentType.getMessage())).orElse(""),
                Optional.ofNullable(contentLength).map(contentLength -> String.format("Content-Length: %d\r\n", contentLength)).orElse(""),
                "\r\n"
        );
    }
}

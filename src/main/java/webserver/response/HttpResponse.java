package webserver.response;

import org.springframework.http.HttpStatus;
import view.View;
import webserver.Cookie;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private ResponseHeader header;
    private byte[] body;

    public HttpResponse(ResponseHeader header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public static HttpResponse ok(HttpRequest request) {
        return new HttpResponse(ResponseHeader.ok(request), "".getBytes());
    }

    public static HttpResponse error() {
        return new HttpResponse(ResponseHeader.error(), "".getBytes());
    }

    public void setBody(byte[] body) {
        this.body = body;
        this.header.setContentLength(body.length);
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setView(View view) {
        if (view != null) {
            this.header.setContentType(view.getContentType());
            setBody(view.getContent());
        } else {
            this.header.setStatus(HttpStatus.NOT_FOUND);
        }
    }

    public void setRedirect(HttpRequest request, String path) {
        this.header.setStatus(HttpStatus.FOUND);
        this.header.setLocation("http://" + request.getHeader().getHost() + path);
    }

    public void setRedirectWithCookie(HttpRequest request, Cookie cookie, String path) {
        setRedirect(request, path);
        this.header.setCookie(cookie);
    }

    public void write(DataOutputStream out) {
        System.out.println(getHeader().toString());
        try {
            out.writeBytes(getHeader().toString());
            out.write(getBody(), 0, getBody().length);
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

package dto;


import utils.FileIoUtils;
import webserver.FileMapping;
import webserver.HttpStatusMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private OutputStream out;
    private String status;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body = new byte[0];

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void setBody(byte[] body, String contentType) {
        this.body = body;
        this.headers.put("Content-Type", contentType);
        this.headers.put("Content-Length", "" + body.length);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setCookie(String logined, String path, String sessionId){
        addHeader("Set-Cookie", "logined=" + logined + "; Path=" + path + (sessionId == null? "":"; " + sessionId));
    }

    public String getHeaders() {
        StringBuilder message = new StringBuilder();
        message.append(status + "\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            message.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        message.append("\r\n");

        return String.valueOf(message);
    }

    public byte[] getBody() {
        return body;
    }

    public void forward(String path) {
        try {
            forwardBody(FileIoUtils.loadFileFromClasspath(FileMapping.getFileURL(path)), FileMapping.getContentType(path));

        } catch (IOException e) {
            badRequest();
        } catch(URISyntaxException e){
            badRequest();
        } catch(Exception e){
            internalServerError();
        }
    }

    public void forwardBody(byte[] body, String contentType) {
        setBody(body, contentType);
        ok();
    }

    public void sendRedirect(String location) {
        this.status = HttpStatusMessage.redirect;
        addHeader("Location", location);
        sendHttpResponse();
    }

    private void sendHttpResponse() {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(getHeaders());
            dos.write(getBody(), 0, getBody().length);
            dos.flush();
        } catch (IOException e) {

        }
    }

    public void ok() {
        this.status = HttpStatusMessage.ok;

        sendHttpResponse();
    }

    public void badRequest() {
        this.status = HttpStatusMessage.badRequest;

        sendHttpResponse();
    }

    public void notFound() {
        this.status = HttpStatusMessage.notFount;

        sendHttpResponse();
    }

    public void internalServerError() {
        this.status = HttpStatusMessage.internalServerError;

        sendHttpResponse();
    }
}
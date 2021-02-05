package model;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private final ResponseHeader header;
    private final DataOutputStream dos;
    private final Body body;

    public Response(ResponseHeader header, DataOutputStream dos, Body body) {
        this.header = header;
        this.dos = dos;
        this.body = body;
    }

    public static Response of(OutputStream out) {
        return new Response(new ResponseHeader(), new DataOutputStream(out), new Body());
    }

    public void addHeader(String key, String value) {
        this.header.addHeader(key, value);
    }

    public void forward(String path) {
        addContentType(path);
        response200Header(this.body.getLength());
        try {
            dos.write(this.body.getBytes());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addContentType(String path) {
        int pos = path.lastIndexOf( "." );
        String ext = path.substring( pos + 1 ).toUpperCase();
        body.setBody(FileIoUtils.loadFileFromClasspath(FileIoUtils.getDirectoryPath(ext) + path));
        header.addHeader("Content-Type", ContentType.valueOf(ext).getMimetype());
    }

    public void forwardBody(String body) {
        this.body.setBody(body.getBytes());
        response200Header(this.body.getLength());
        try {
            dos.write(this.body.getBytes());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        response.append(header.toString());
        response.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        response.append("\r\n");

        try {
            dos.write(response.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRedirect(String path) {
        response302Header(path);
    }

    private void response302Header(String redirectUrl) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 302 Found \r\n");
        response.append("Location: http://localhost:8080" + redirectUrl + "\r\n");
        response.append(header.toString());
        response.append("\r\n");
        try {
            dos.write(response.toString().getBytes());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

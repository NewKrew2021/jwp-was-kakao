package model;

import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private Map<String, String> header;
    private DataOutputStream dos;
    private byte[] body = new byte[0];

    public Response(OutputStream out){
        dos = new DataOutputStream(out);
        header = new HashMap<>();
    }

    public void setBody(String body){
        this.body=body.getBytes();
    }

    public void addHeader(String key, String value){
        this.header.put(key, value);
    }

    public void forward(String path) {
        addContentType(path);
        response200Header(body.length);
        try {
            dos.write(body);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addContentType(String path){
        if(path.contains(".html")){
            this.body=FileIoUtils.loadFileFromClasspath("templates"+path);
            header.put("Content-Type","text/html");
        }
        if(path.contains(".css")){
            this.body=FileIoUtils.loadFileFromClasspath("static"+path);
            header.put("Content-Type","text/css");
        }
        if(path.contains(".js")){
            this.body=FileIoUtils.loadFileFromClasspath("static"+path);
            header.put("Content-Type","application/javascript");
        }
    }

    public void forwardBody(String body) {
        this.body=body.getBytes();
        response200Header(this.body.length);
        try {
            dos.write(this.body);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        StringBuilder response=new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        for (String key : header.keySet()) {
            response.append(key+": "+header.get(key)+"\r\n");
        }
        response.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        response.append("\r\n");

        try {
            dos.write(response.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRedirect(String path){
        response302Header(path);
    }

    private void response302Header(String redirectUrl) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 302 Found \r\n");
        response.append("Location: http://localhost:8080"+redirectUrl+"\r\n");
        for (String key : header.keySet()) {
            response.append(key+": "+header.get(key)+"\r\n");
        }
        response.append("\r\n");
        try {
            dos.write(response.toString().getBytes());
            dos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

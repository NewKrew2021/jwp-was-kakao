package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private String path;
    private String header;
    private String body;
    private String cookie;

    public Response(){
        this.cookie="\r\n";
    }

    public void body(String body){
        this.body=body;
    }

    public void setPath(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setResponse200Header(int lengthOfBodyContent) {
        StringBuilder response=new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        response.append("Content-Type: text/html;charset=utf-8\r\n");
        response.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        this.header = response.toString();
    }

    public void setResponse200Header() {
        StringBuilder response=new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        response.append("Content-Type: text/html;charset=utf-8\r\n");
        this.header= response.toString();
    }

    public void setResponse302Header(String redirectUrl) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 302 Found \r\n");
        response.append("Location: http://localhost:8080"+redirectUrl+"\r\n");
        this.header= response.toString();
    }

    public void setCookie(String cookie){
        this.cookie=cookie;
    }

    public String getCookie() {
        return cookie;
    }
}

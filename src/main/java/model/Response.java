package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private String path;
    private String header;
    private byte[] body;
    private String cookie;

    public Response(){
        this.cookie="\r\n";

    }

    public void setBody(String body){
        this.body=body.getBytes();
    }

    public void setPath(String path) {
        this.path=path;
        this.body=filePathToBytes(path);
    }

    public String getPath() {
        return path;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getBody() {
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
    public void setCssResponse200Header() {
        StringBuilder response=new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        response.append("Content-Type: text/css;charset=utf-8\r\n");
        this.header= response.toString();
    }

    public void setResponse302Header(String redirectUrl) {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 302 Found \r\n");
        response.append("Location: http://localhost:8080"+redirectUrl+"\r\n");
        this.header= response.toString();
    }

    public void setJsResponse200Header() {
        StringBuilder response=new StringBuilder();
        response.append("HTTP/1.1 200 OK \r\n");
        response.append("Content-Type: application/javascript;charset=utf-8\r\n");
        this.header= response.toString();
    }

    public void setCookie(String cookie){
        this.cookie=cookie;
    }

    public String getCookie() {
        return cookie;
    }


    private byte[] filePathToBytes(String path) {
        String[] paths= path.split("\\.");
        if(paths.length>=2&&(paths[1].equals("html")||paths[1].equals("ico"))){
            return FileIoUtils.loadFileFromClasspath("templates" + path);
        }
        return FileIoUtils.loadFileFromClasspath("static"+ path);
    }

}

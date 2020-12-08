package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final HttpHeader httpHeader;
    private final RequestBody body;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        httpHeader = new HttpHeader(bufferedReader);
        // 요구사항 1
        httpHeader.print();
        if (getMethod().equals(HttpMethod.POST) || getMethod().equals(HttpMethod.DELETE) || getMethod().equals(HttpMethod.PUT)) {
            body = new RequestBody(IOUtils.readData(bufferedReader, Integer.parseInt(getContentLength())));
        } else if(getMethod().equals(HttpMethod.GET)) {
            body = new RequestBody(getQueryString());
        } else{
            body = new RequestBody("");
        }
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public HttpMethod getMethod() {
        return HttpMethod.valueOf(httpHeader.getHttpHeader("Method"));
    }

    public String getPath() {
        return httpHeader.getHttpHeader("Path");
    }

    public String getQueryString() {
        String[] paths = httpHeader.getHttpHeader("Path").split("\\?");
        if(paths.length > 1){
            return paths[1];
        }else{
            return "";
        }
    }

    public String getProtocol() {
        return httpHeader.getHttpHeader("Protocol");
    }

    public String getHost() {
        return httpHeader.getHttpHeader("Host");
    }

    public String getConnection() {
        return httpHeader.getHttpHeader("Connection");
    }

    public String getAccept() {
        return httpHeader.getHttpHeader("Accept");
    }

    public String getContentLength() {
        return httpHeader.getHttpHeader("Content-Length");
    }

    public RequestBody getBody() {
        return body;
    }

    public boolean isLogin(){
        return httpHeader.getCookies().get("logined") != null
                && httpHeader.getCookies().get("logined").equals("true");
    }

}

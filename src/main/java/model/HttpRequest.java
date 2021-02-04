package model;

import exception.http.IllegalHeaderException;
import exception.http.IllegalHttpRequestException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;

    private String body = "";

    public HttpRequest(InputStream in) throws IOException {
        this(new BufferedReader(new InputStreamReader(in)));
    }

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        httpRequestLine = new HttpRequestLine(line);
        httpRequestHeader = new HttpRequestHeader(br);
        setBody(br);
    }

    private void setBody(BufferedReader br) throws IOException {
        while(br.ready()){
            body = IOUtils.readData(br, Integer.parseInt(getHeader("Content-Length")));
        }
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getParsedBody(){
        Map<String, String> parsedBody = new HashMap<>();
        String[] queryParams = body.split("&");
        Arrays.stream(queryParams).forEach((param) -> {
            String[] args = param.split("=", 2);
            parsedBody.put(args[0].trim(), args[1].trim());
        });
        return parsedBody;
    }

    public String getCookie(String cookieParam){
        Map<String, String> parsedCookie = new HashMap<>();
        String cookieHeader = getHeader("Cookie");

        String[] cookies = cookieHeader.split(";");
        Arrays.stream(cookies).forEach((cookie) -> {
           String[] args = cookie.split("=", 2);
           parsedCookie.put(args[0].trim(), args[1].trim());
        });
        return parsedCookie.get(cookieParam);
    }

    public Map<String, String> getHeaderMap() {
        return httpRequestHeader.getHeaderMap();
    }

    public String getHeader(String key){
        return httpRequestHeader.getHeader(key);
    }

    public String getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public String getProtocol() {
        return httpRequestLine.getProtocol();
    }

    public Map<String, String> getQueryParameterMap() {
        return httpRequestLine.getQueryParameterMap();
    }
}

package model;

import exception.http.IllegalHeaderException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final Map<String, String> headerMap = new HashMap<>();
    private final Map<String, String> queryParameterMap = new HashMap<>();

    private HttpMethod method;
    private String path;
    private String protocol;
    private String body = "";

    public HttpRequest(InputStream in) throws IOException {
        this(new BufferedReader(new InputStreamReader(in)));
    }

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        setStartLine(line);
        setHeader(br);
        setBody(br);
    }

    private void setBody(BufferedReader br) throws IOException {
        while(br.ready()){
            body = IOUtils.readData(br, Integer.parseInt(headerMap.get("Content-Length")));
        }
    }

    private void setHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!(line == null || line.trim().equals(""))) {
            String[] headerLine = line.split(":", 2);
            validateHeaderLine(headerLine);
            headerMap.put(headerLine[0].trim(), headerLine[1].trim());
            line = br.readLine();
        }
    }

    private void validateHeaderLine(String[] headerLine) throws IOException {
        if (headerLine.length < 2) {
            throw new IOException();
        }
    }

    private void setStartLine(String line) throws IOException {
        String[] startLine = line.split(" ");
        validateStartLine(startLine);
        method = HttpMethod.valueOf(startLine[0]);
        setPath(startLine[1]);
        protocol = startLine[2];
    }

    private void setPath(String path) {
        String[] pathArgs = path.split("\\?", 2);
        this.path = pathArgs[0];
        if (pathArgs.length == 1) {
            return;
        }
        setQueryParameters(pathArgs[1]);
    }

    private void setQueryParameters(String pathArg) {
        String[] queryParams = pathArg.split("&");
        Arrays.stream(queryParams).forEach((param) -> {
            String[] args = param.split("=", 2);
            queryParameterMap.put(args[0].trim(), args[1].trim());
        });
    }

    private static void validateStartLine(String[] startLine) throws IOException {
        if (startLine.length != 3 || !startLine[1].startsWith("/")) {
            throw new IOException();
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getHeader(String key){
        String header = headerMap.get(key);
        if(header == null) {
            throw new IllegalHeaderException();
        }
        return header;
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

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }
}
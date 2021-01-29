package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private String protocol;
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> queryParameterMap = new HashMap<>();
    private String body = "";

    private final static List<String> acceptedMethod = Arrays.asList("GET", "POST", "PUT", "DELETE");

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(InputStream in) throws IOException {
        this(new BufferedReader(new InputStreamReader(in)));
    }

    public HttpRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        setStartLine(line);
        setHeaderLine(br);
        setBody(br);
    }

    private void setBody(BufferedReader br) throws IOException {
        while(br.ready()){
            body += br.readLine();
        }
        log.debug("{}", body);
    }

    private void setHeaderLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!(line == null || line.trim().equals(""))) {
            log.debug("{}", line);
            String[] headerLine = line.split(":", 2);
            validateHeaderLine(headerLine);
            headerMap.put(headerLine[0], headerLine[1].trim());
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
        log.debug(line);
        method = startLine[0];
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
            queryParameterMap.put(args[0], args[1]);
        });
    }

    private static void validateStartLine(String[] startLine) throws IOException {
        if (startLine.length != 3 || !acceptedMethod.contains(startLine[0]) || !startLine[1].startsWith("/")) {
            throw new IOException();
        }
    }

    public String getMethod() {
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

    public String getBody() {
        return body;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }
}

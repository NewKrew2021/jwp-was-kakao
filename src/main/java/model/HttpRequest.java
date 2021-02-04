package model;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final Map<String, String> headerMap = new HashMap<>();
    private final Map<String, String> queryParameterMap = new HashMap<>();
    private final Map<String, String> cookieMap = new HashMap<>();

    private String remoteAddress;
    private HttpMethod method;
    private String path;
    private String protocol;
    private String body = "";

    public HttpRequest(InputStream in, Socket connection) throws IOException {
        this(new BufferedReader(new InputStreamReader(in)), connection);
    }

    public HttpRequest(BufferedReader br, Socket connection) throws IOException {
        String line = br.readLine();
        setStartLine(line);
        setHeader(br);
        setBody(br);
        setCookie();
        setRemoteAddress(connection);
    }

    private void setStartLine(String line) throws IOException {
        String[] startLine = line.split(" ");
        validateStartLine(startLine);
        method = HttpMethod.valueOf(startLine[0]);
        setPath(startLine[1]);
        protocol = startLine[2];
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

    private void setBody(BufferedReader br) throws IOException {
        while (br.ready()) {
            body = IOUtils.readData(br, Integer.parseInt(headerMap.get("Content-Length")));
        }
    }

    public void setCookie() {
        String cookieHeader = getHeader("Cookie");
        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split(";");
            Arrays.stream(cookies).forEach((cookie) -> {
                String[] args = cookie.split("=", 2);
                cookieMap.put(args[0].trim(), args[1].trim());
            });
        }
    }

    private void setRemoteAddress(Socket connection) {
        String defaultAddress = String.valueOf(connection.getInetAddress())
                .replaceAll("/", "");
        remoteAddress = Arrays.stream(IpHeader.headers)
                .filter(header -> headerMap.get(header) != null)
                .findFirst()
                .orElse(defaultAddress);
    }

    private void validateHeaderLine(String[] headerLine) throws IOException {
        if (headerLine.length < 2) {
            throw new IOException();
        }
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

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getBody() {
        return body;
    }

    public String getCookie(String cookieParam) {
        return cookieMap.get(cookieParam);
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public Map<String, String> getParsedBody() {
        Map<String, String> parsedBody = new HashMap<>();
        String[] queryParams = body.split("&");
        Arrays.stream(queryParams).forEach((param) -> {
            String[] args = param.split("=", 2);
            parsedBody.put(args[0].trim(), args[1].trim());
        });
        return parsedBody;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }
}

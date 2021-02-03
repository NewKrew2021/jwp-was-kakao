package webserver.http;

import annotation.web.RequestMethod;
import com.google.common.collect.Maps;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

public class HttpRequest {
    private final RequestMethod requestMethod;
    private final String path;
    private final String httpVersion;
    private final String rawQueryString;
    private final Map<String, String> headers = Maps.newHashMap();
    private final Map<String, String> parameters = Maps.newHashMap();

    public HttpRequest(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] token = getNextLine(br).split(" ");

        checkValidRequest(token);

        String[] uri = token[1].split("\\?");

        requestMethod = RequestMethod.of(token[0]);
        path = uri[0];
        rawQueryString = (uri.length > 1) ? uri[1] : "";
        httpVersion = token[2];

        makeHeaders(br);
        makeParameters(rawQueryString);
        makeParameters(getBodyData(br));
    }

    public String getMethod() {
        return requestMethod.toString();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return headers.getOrDefault(header, "");
    }

    public String getParameter(String parameter) {
        return parameters.getOrDefault(parameter, "");
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    private void makeHeaders(BufferedReader br) {
        for (String line = getNextLine(br); !"".equals(line) && line != null; line = getNextLine(br)) {
            String[] tokens = line.split(":");
            headers.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    private void makeParameters(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        Stream.of(line.split("&"))
                .forEach(this::insertDecodedParameter);
    }

    private String getBodyData(BufferedReader br) {
        try {
            int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nError while reading body data");
        }
    }

    private String getNextLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nError while reading from input stream");
        }
    }

    private void insertDecodedParameter(String parameter) {
        try {
            String[] pair = parameter.split("=");
            parameters.put(pair[0], URLDecoder.decode(pair[1], StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException ignored) {

        }
    }

    private void checkValidRequest(String[] token) {
        if (token.length < 3) {
            throw new IllegalArgumentException("Incorrect request");
        }
    }
}

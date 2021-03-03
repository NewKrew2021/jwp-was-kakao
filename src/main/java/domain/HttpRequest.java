package domain;

import org.springframework.http.HttpMethod;
import utils.IOUtils;
import utils.KeyValueTokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public static final String HEADER_DELIMITER = ": ";
    public static final String CONTENT_LENGTH = "Content-Length";

    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeaders httpHeaders;
    private final HttpParameters httpParameters;

    private HttpRequest(HttpRequestStartLine startLine, HttpHeaders httpHeaders, HttpParameters httpParameters) {
        this.httpRequestStartLine = startLine;
        this.httpHeaders = httpHeaders;
        this.httpParameters = httpParameters;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequestStartLine startLine = HttpRequestStartLine.of(br.readLine());
        Map<String, String> params = new HashMap<>();
        if (startLine.hasParameter()) {
            params = KeyValueTokenizer.of(startLine.getParameter());
        }

        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while ((headerLine = br.readLine()) != null && !headerLine.isEmpty()) {
            addHeader(headers, headerLine);
        }

        if (headers.containsKey(CONTENT_LENGTH)) {
            String content = IOUtils.readData(br, Integer.parseInt(headers.get(CONTENT_LENGTH)));
            params.putAll(KeyValueTokenizer.of(content));
        }

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpParameters httpParameters = new HttpParameters(params);
        return new HttpRequest(startLine, httpHeaders, httpParameters);
    }

    private static void addHeader(Map<String, String> headers, String line) {
        String[] header = line.split(HEADER_DELIMITER);
        headers.put(header[0], header[1]);
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return httpParameters.getParameter(key);
    }

    public HttpMethod getMethod() {
        return httpRequestStartLine.getMethod();
    }

    public String getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public Map<String, String> getParameters() {
        return httpParameters.getParams();
    }
}

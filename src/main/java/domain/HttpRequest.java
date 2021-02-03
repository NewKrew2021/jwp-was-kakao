package domain;

import org.springframework.http.HttpMethod;
import utils.IOUtils;
import utils.KeyValueTokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public static final String NO_KEY = "No Key";
    public static final String HEADER_DELIMITER = ": ";
    public static final String CONTENT_LENGTH = "Content-Length";

    private final HttpRequestStartLine startLine;
    private Map<String, String> parameters = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequest(BufferedReader br) throws IOException {
        startLine = HttpRequestStartLine.of(br.readLine());
        if (startLine.hasParameter()) {
            parameters = KeyValueTokenizer.of(startLine.getParameter());
        }

        String headerLine;
        while ((headerLine = br.readLine()) != null && !headerLine.isEmpty()) {
            addHeader(headerLine);
        }

        if (headers.containsKey(CONTENT_LENGTH)) {
            parameters.putAll(KeyValueTokenizer.of(IOUtils.readData(br, Integer.parseInt(headers.get(CONTENT_LENGTH)))));
        }
    }

    private void addHeader(String line) {
        String[] header = line.split(HEADER_DELIMITER);
        headers.put(header[0], header[1]);
    }

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public String getUrl() {
        return startLine.getUrl();
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, NO_KEY);
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, NO_KEY);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}

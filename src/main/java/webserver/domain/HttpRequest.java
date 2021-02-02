package webserver.domain;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String body;
    private String path;
    private HttpMethod method;

    public HttpRequest(InputStream in) throws IOException {
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String[] firstLineTokens = reader.readLine().split(" ");
        String requestUrl = firstLineTokens[1];
        parseUrl(requestUrl);
        readHeaders(reader);

        this.method = HttpMethod.valueOf(firstLineTokens[0]);
        if (this.headers.containsKey(HttpHeader.CONTENT_LENGTH)) {
            this.body = IOUtils.readData(reader, Integer.parseInt(this.headers.get("Content-Length")));
            parseParameter(this.body);
        }
    }

    private void readHeaders(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (isValid(line)) {
            String[] header = line.split(": ");
            this.headers.put(header[0], header[1]);
            line = reader.readLine();
        }
    }

    private boolean isValid(String line) {
        return (line != null) && !"".equals(line);
    }

    private void parseUrl(String requestUrl) {
        if (requestUrl.indexOf('?') == -1) {
            this.path = requestUrl;
            return;
        }
        String[] urlToken = requestUrl.split("\\?");
        this.path = urlToken[0];
        parseParameter(urlToken[1]);
    }

    private void parseParameter(String parameterString) {
        String[] parameterToken = parameterString.split("&");
        Arrays.stream(parameterToken)
                .forEach(token -> {
                    if (token.indexOf('=') == -1) {
                        this.parameters.put(token, null);
                        return;
                    }
                    String[] keyValue = token.split("=");
                    this.parameters.put(keyValue[0], keyValue[1]);
                });
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, "");
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }

    public String getBody() {
        if (body == null) {
            return "";
        }
        return body;
    }
}

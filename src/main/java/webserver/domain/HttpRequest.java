package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String body;
    private String path;
    private HttpMethod method;

    public HttpRequest(InputStream in) throws IOException {
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String[] requestLine = reader.readLine().split(" ");
        String requestUrl = requestLine[1];

        parseParameterByQueryString(requestUrl);
        parseHeaders(reader);

        this.method = HttpMethod.valueOf(requestLine[0]);
        if (this.headers.containsKey(HttpHeader.CONTENT_LENGTH)) {
            this.body = IOUtils.readData(reader, Integer.parseInt(this.headers.get(HttpHeader.CONTENT_LENGTH)));
            parseParameter(this.body);
        }
    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (isValid(line)) {
            String[] header = line.split(": ");
            this.headers.put(header[0], header[1]);
            line = reader.readLine();
        }
    }

    private boolean isValid(String line) {
        return !StringUtils.isEmpty(line);
    }

    private void parseParameterByQueryString(String requestUrl) {
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

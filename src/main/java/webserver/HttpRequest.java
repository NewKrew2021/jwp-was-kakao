package webserver;

import exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class HttpRequest {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> header;
    private final Map<String, String> parameters;

    private HttpRequest(HttpMethod httpMethod, String path, Map<String, String> header, Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.header = Collections.unmodifiableMap(header);
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static HttpRequest of(HttpMethod httpMethod, String path, Map<String, String> header, Map<String, String> parameters) {
        return new HttpRequest(httpMethod, path, header, parameters);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        if (!header.containsKey(key)) {
            String message = key + "(이)가 존재하지 않습니다.";
            logger.error(message);
            throw new NotExistException(message);
        }

        return header.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void logRequestMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========= Request Message =========\n")
                .append(httpMethod.toString()).append(" ")
                .append(path).append(" ")
                .append(HTTP_VERSION).append("\n");
        for (Map.Entry<String, String> entries : header.entrySet()) {
            sb.append(entries.getKey())
                    .append(": ").append(entries.getValue()).append("\n");
        }
        sb.append("----------------------------------");
        String message = sb.toString();
        logger.debug(message);
    }
}

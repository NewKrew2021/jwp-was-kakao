package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String[] templateList = new String[]{".html", ".ico"};
    public static final String BLANK_DELIMITER = " ";
    public static final String HEADER_DELIMITER = ": ";
    public static final char QUERY_STRING_QUESTION_MARK = '?';
    public static final String QUERY_STRING_QUESTION_MARK_REGEX = "\\?";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String PARAMETER_EQUAL_MARK = "=";
    public static final String DEFAULT_VALUE = "";

    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String body;
    private String path;
    private HttpMethod method;

    public HttpRequest(InputStream in) throws IOException {
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String[] requestLine = reader.readLine().split(BLANK_DELIMITER);
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
            String[] header = line.split(HEADER_DELIMITER);
            this.headers.put(header[0], header[1]);
            line = reader.readLine();
        }
    }

    private boolean isValid(String line) {
        return !StringUtils.isEmpty(line);
    }

    private void parseParameterByQueryString(String requestUrl) {
        if (requestUrl.indexOf(QUERY_STRING_QUESTION_MARK) == -1) {
            this.path = requestUrl;
            return;
        }
        String[] urlToken = requestUrl.split(QUERY_STRING_QUESTION_MARK_REGEX);
        this.path = urlToken[0];
        parseParameter(urlToken[1]);
    }

    private void parseParameter(String parameterString) {
        String[] parameterToken = parameterString.split(PARAMETER_DELIMITER);
        Arrays.stream(parameterToken)
                .forEach(token -> {
                    if (!token.contains(PARAMETER_EQUAL_MARK)) {
                        this.parameters.put(token, null);
                        return;
                    }
                    String[] keyValue = token.split(PARAMETER_EQUAL_MARK);
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
        return headers.getOrDefault(key, DEFAULT_VALUE);
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, DEFAULT_VALUE);
    }

    public String getBody() {
        if (body == null) {
            return DEFAULT_VALUE;
        }
        return body;
    }

    public boolean containsCookie(String cookie) {
        String requestCookie = this.headers.get(HttpHeader.COOKIE);
        if (requestCookie != null) {
            return requestCookie.contains(cookie);
        }
        return false;
    }

    public Cookies getCookies() {
        return new Cookies(headers.get(HttpHeader.COOKIE));
    }

    public boolean isTemplate() {
        return Arrays.stream(templateList).anyMatch(str -> path.contains(str));
    }
}

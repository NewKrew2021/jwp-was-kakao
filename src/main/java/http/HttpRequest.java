package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.IOUtils;
import utils.ParseUtils;
import webserver.RequestHandler;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class HttpRequest {
    public static final String REQUEST_LINE_PATTERN = "(GET|POST)\\s+([^?]+)(?:\\?(.+))?\\s+(HTTP/.+)";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int METHOD = 1;
    private static final int REQUEST_URI = 2;
    private static final int PARAMETER = 3;
    private static final int HTTP_VERSION = 4;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();
    private final Map<String, Cookie> cookies = new HashMap<>();
    private HttpMethod method;
    private URI uri;
    private String httpVersion;

    private HttpRequest(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        initializeRequestLine(br);
        initializeHeader(br);
        initializeParameter(br);
        initializeCookie();
    }

    public static HttpRequest of(InputStream in) throws Exception {
        return new HttpRequest(in);
    }

    private void initializeCookie() {
        if (headers.containsKey("Cookie")) {
            mapCookie(headers.get("Cookie"));
        }
    }

    private void initializeRequestLine(BufferedReader br) throws Exception {
        String requestLine = br.readLine();
        logger.debug("request line : {}", requestLine);

        Matcher matcher = ParseUtils.getMatcher(REQUEST_LINE_PATTERN, requestLine);

        method = HttpMethod.valueOf(matcher.group(METHOD));
        uri = URI.create(matcher.group(REQUEST_URI));
        httpVersion = matcher.group(HTTP_VERSION);

        if (matcher.group(PARAMETER) != null) {
            mapParameter(matcher.group(PARAMETER));
        }
    }

    private void initializeParameter(BufferedReader br) throws IOException {
        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            mapParameter(body);
            logger.debug("body : {} ", body);
        }
    }

    private void initializeHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!"".equals(line) && line != null) {
            logger.debug("header : {}", line);
            mapHeader(line);
            line = br.readLine();
        }
    }

    private void mapParameter(String parameterText) throws UnsupportedEncodingException {
        ParseUtils.parseRequestParameters(parameterText)
                .forEach((key, value) -> parameters.put(key, value));
    }

    private void mapHeader(String headerText) {
        if (!headerText.equals("")) {
            Map.Entry<String, String> elem = ParseUtils.parseParametersByColon(headerText);
            headers.put(elem.getKey(), elem.getValue());
        }
    }

    private void mapCookie(String cookieText) {
        ParseUtils.parseCookie(cookieText)
                .forEach((key, value) -> cookies.put(key, Cookie.of(key, value, "/")));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri.toString();
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Cookie getCookie(String key) {
        return cookies.getOrDefault(key, Cookie.of());
    }
}

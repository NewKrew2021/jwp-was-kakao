package webserver.model;

import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Pattern headerSplitPattern = Pattern.compile(" *: *");
    private static final Pattern querySplitPattern = Pattern.compile("&");
    private static final Pattern keyValuePattern = Pattern.compile("=");
    private static final Pattern cookieSplitPattern = Pattern.compile(" *; *");

    private final HttpRequestLine httpRequestLine;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final Map<String, String> cookies;

    private HttpRequest(HttpRequestLine httpRequestLine, Map<String, String> headers, Map<String, String> parameters) {
        this.httpRequestLine = httpRequestLine;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = parseCookie(headers.getOrDefault("cookie", ""));
    }

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        HttpRequestLine httpRequestLine = HttpRequestLine.from((reader.readLine()));
        Map<String, String> headers = parseHeaders(reader);

        HttpMethod method = httpRequestLine.getMethod();
        String query = httpRequestLine.getQuery();

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            query = IOUtils.readData(reader, Integer.parseInt(headers.getOrDefault("content-length", "0")));
        }

        return new HttpRequest(httpRequestLine, headers, parseQuery(query));
    }

    private static Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!isEmpty(line = reader.readLine())) {
            String[] keyValue = headerSplitPattern.split(line, 2);
            headers.put(keyValue[0].trim().toLowerCase(), keyValue[1].trim());
        }
        return headers;
    }

    private static boolean isEmpty(String string) {
        return Objects.isNull(string) || string.isEmpty();
    }

    private static Map<String, String> parseQuery(String query) {
        if (isEmpty(query)) {
            return Collections.emptyMap();
        }

        return querySplitPattern.splitAsStream(query)
                .map(keyValuePattern::split)
                .filter(s -> s.length == 2)
                .collect(Collectors.toMap(s -> s[0], s -> safeDecode(s[1]), (l, r) -> l));
    }

    private static Map<String, String> parseCookie(String cookieHeader) {
        return cookieSplitPattern.splitAsStream(cookieHeader)
                .map(keyValuePattern::split)
                .filter(s -> s.length == 2)
                .collect(Collectors.toMap(s -> s[0], s -> safeDecode(s[1]), (l, r) -> r));
    }

    private static String safeDecode(String encodedUrl) {
        try {
            return URLDecoder.decode(encodedUrl, "utf8");
        } catch (UnsupportedEncodingException e) {
            return encodedUrl;
        }
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return Collections.unmodifiableMap(cookies);
    }
}

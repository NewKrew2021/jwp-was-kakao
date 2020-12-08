package webserver.model;

import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Request {
    private static final Pattern requestLinePattern = Pattern.compile(
            "(?<method>[A-Z]+)" +         // request method
            " +(?<path>/[^?# ]*)" +        // resource path (only path part)
            "(?:\\?(?<query>[^# ]+))?" +  // query string (optional)
            "(?:#(?<hash>[^ ]+))?" +      // hash (optional)
            "(?: +(?<version>.*))?"       // version (optional)
    );
    private static final Pattern headerSplitPattern = Pattern.compile(" *: *");
    private static final Pattern querySplitPattern = Pattern.compile("&");
    private static final Pattern keyValuePattern = Pattern.compile("=");
    private static final Pattern cookieSplitPattern = Pattern.compile(" *; *");

    private final HttpMethod method;
    private final String path;
    private final String query;
    private final String hash;
    private final String version;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final Map<String, String> cookies;

    private Request(HttpMethod method, String path, String version, String query, String hash, Map<String, String> headers, Map<String, String> parameters) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.query = query;
        this.hash = hash;
        this.headers = headers;
        this.parameters = parameters;
        this.cookies = parseCookie(headers.getOrDefault("cookie", ""));
    }

    public static Request from(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Matcher matcher = parseRequestLine(reader.readLine());
        Map<String, String> headers = parseHeaders(reader);

        HttpMethod method = HttpMethod.valueOf(matcher.group("method"));
        String query = matcher.group("query");

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            query = IOUtils.readData(reader, Integer.parseInt(headers.getOrDefault("content-length", "0")));
        }

        return new Request(
                method,
                matcher.group("path"),
                matcher.group("version"),
                matcher.group("query"),
                matcher.group("hash"),
                headers,
                parseQuery(query)
        );
    }

    private static Matcher parseRequestLine(String requestLine) {
        Matcher matcher;
        if (Objects.isNull(requestLine) || !(matcher = requestLinePattern.matcher(requestLine)).find()) {
            throw new IllegalArgumentException("Unexpected Request-Line message");
        }
        return matcher;
    }

    private static Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (isNotEmpty(line = reader.readLine())) {
            String[] keyValue = headerSplitPattern.split(line, 2);
            headers.put(keyValue[0].trim().toLowerCase(), keyValue[1].trim());
        }
        return headers;
    }

    private static boolean isNotEmpty(String string) {
        return !Objects.isNull(string) && !string.isEmpty();
    }

    private static Map<String, String> parseQuery(String query) {
        if (Objects.isNull(query) || query.trim().isEmpty()) {
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
        return path;
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

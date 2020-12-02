package webserver.model;

import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private static final Pattern querySplitPattern = Pattern.compile("&");
    private static final Pattern keyValuePattern = Pattern.compile("=");

    private final String method;
    private final String path;
    private final String query;
    private final String version;
    private final Map<String, String> parameters;

    public Request(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Matcher matcher = parseRequestLine(reader.readLine());
        method = matcher.group("method");
        path = matcher.group("path");
        query = matcher.group("query");
        version = matcher.group("version");
        parameters = parseQuery(query);
    }

    private Matcher parseRequestLine(String requestLine) {
        Matcher matcher;
        if (Objects.isNull(requestLine) || !(matcher = requestLinePattern.matcher(requestLine)).find()) {
            throw new IllegalArgumentException("Unexpected Request-Line message");
        }
        return matcher;
    }

    private Map<String, String> parseQuery(String query) {
        if (Objects.isNull(query) || query.trim().isEmpty()) {
            return Collections.emptyMap();
        }
        return querySplitPattern.splitAsStream(query)
                .map(keyValuePattern::split)
                .collect(Collectors.toMap(s -> s[0], s -> safeDecode(s[1]), (l, r) -> l));
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

    public byte[] getRequestedResource() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath("./templates" + path);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }
}

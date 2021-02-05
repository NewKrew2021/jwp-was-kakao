package webserver.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.ObjectUtils;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private static final Pattern bodyPattern = Pattern.compile("&");
    private static final Pattern headerPattern = Pattern.compile(" *: *");
    private static final Pattern keyValuePattern = Pattern.compile("=");
    private static final Pattern requestLinePattern = Pattern.compile(
            "(?<method>[A-Z]+)" +         // request method
                    " +(?<path>/[^?# ]*)" +        // resource path (only path part)
                    "(?:\\?(?<query>[^# ]+))?" +  // query string (optional)
                    "(?: +(?<version>.*))?"       // version (optional)
    );

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String LOGIN_COOKIE = "logined=true";
    private static final String DEFAULT_LOGIN_COOKIE = "logined=false";
    private static final String DEFAULT_CONTENT_LENGTH = "0";

    private Map<String, String> httpHeader = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private HttpMethod httpMethod;
    private String path;
    private String version;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String line = bufferedReader.readLine();
        parseFirstLine(line);

        parseHeader(bufferedReader);

        int bodyLength = Integer.parseInt(httpHeader.getOrDefault(HttpHeaders.CONTENT_LENGTH, DEFAULT_CONTENT_LENGTH));
        String body = IOUtils.readData(bufferedReader, bodyLength);
        parseBody(body);
    }

    private void parseFirstLine(String line) {
        Matcher matcher = requestLinePattern.matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid request line");
        }

        httpMethod = HttpMethod.resolve(matcher.group("method"));
        path = matcher.group("path");
        version = matcher.group("version");
        String query = matcher.group("query");

        if (!ObjectUtils.isEmpty(query)) {
            parseBody(query);
        }
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (line != null && !line.equals("")) {
            String[] splitted = headerPattern.split(line);
            putHeader(splitted[0], splitted[1]);
            line = bufferedReader.readLine();
        }
    }

    private void parseBody(String body) {
        String[] keyValues = bodyPattern.split(body);
        Arrays.stream(keyValues)
                .map(keyValue -> keyValuePattern.split(keyValue))
                .filter(keyValue -> keyValue.length == 2)
                .forEach(keyValue -> putParameter(keyValue[0], keyValue[1]));
    }

    private void putParameter(String key, String value) {
        try {
            parameters.put(key, URLDecoder.decode(value, DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void putHeader(String key, String value) {
        try {
            httpHeader.put(key, URLDecoder.decode(value, DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public boolean isLogined() {
        return httpHeader.getOrDefault(HttpHeaders.COOKIE, DEFAULT_LOGIN_COOKIE).contains(LOGIN_COOKIE);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key) {
        return httpHeader.getOrDefault(key, "");
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }

}

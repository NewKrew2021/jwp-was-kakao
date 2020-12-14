package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {

    public static final String HOST = "Host";
    public static final String CONNECTION = "Connection";
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String PATH = "Path";
    public static final String METHOD = "Method";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String SEPERATOR = "=";
    public static final String COOKIE_PATH = ";Path=/";
    public static final String CONTENT_TYPE = "Content-Type";
    private static final String COLON_SEPARATOR = ": ";
    public static final String PROTOCOL = "Protocol";
    public static final String COOKIE = "Cookie";
    private final List<String> headerLines;
    private final Map<String, String> httpHeaders = new HashMap<>();
    private final Cookies cookies;

    private HttpHeader(BufferedReader bufferedReader) throws IOException {
        headerLines = new ArrayList<>();
        String line = bufferedReader.readLine();
        headerLines.add(line);
        String[] values = line.split(" ");
        HttpMethod httpMethod = HttpMethod.getHttpMethod(values[0]);
        httpHeaders.put(METHOD, httpMethod.name());
        HttpPath httpPath = HttpPath.from(values[1]);
        httpHeaders.put(PATH, httpPath.toString());
        HttpProtocol httpProtocol = HttpProtocol.from(values[2]);
        httpHeaders.put(PROTOCOL, httpProtocol.toString());
        while (StringUtils.isNotBlank(line = bufferedReader.readLine())) {
            addHeader(line);
            headerLines.add(line);
        }
        this.cookies = Cookies.parse(httpHeaders.getOrDefault(COOKIE, ""));
    }

    public static HttpHeader from(BufferedReader bufferedReader) throws IOException {
        return new HttpHeader(bufferedReader);
    }

    private void addHeader(String line){
        String[] keyValue = line.split(COLON_SEPARATOR);
        httpHeaders.put(keyValue[0], keyValue[1]);
    }

    public String getHttpHeader(String key) {
        return httpHeaders.get(key);
    }

    public Cookies getCookies() {
        return cookies;
    }

    public String getProtocol() {
        return httpHeaders.get(PROTOCOL);
    }


    public void print() {
        System.out.println(String.join("\n", headerLines));
        System.out.println("\n");
    }


}

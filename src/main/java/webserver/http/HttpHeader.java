package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {

    private static final String COLON_SEPARATOR = ": ";
    private final List<String> headerLines;
    private final Map<String, String> httpHeaders = new HashMap<>();
    private final Cookies cookies;

    public HttpHeader(BufferedReader bufferedReader) throws IOException {
        headerLines = new ArrayList<>();
        String line = bufferedReader.readLine();
        headerLines.add(line);
        String[] values = line.split(" ");
        HttpMethod httpMethod = HttpMethod.getHttpMethod(values[0]);
        httpHeaders.put("Method", httpMethod.name());
        HttpPath httpPath = new HttpPath(values[1]);
        httpHeaders.put("Path", httpPath.toString());
        HttpProtocol httpProtocol = new HttpProtocol(values[2]);
        httpHeaders.put("Protocol", httpProtocol.toString());
        while (StringUtils.isNotBlank(line = bufferedReader.readLine())) {
            addHeader(line);
            headerLines.add(line);
        }
        this.cookies = Cookies.parse(httpHeaders.getOrDefault("Cookie", ""));
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

    public void print() {
        System.out.println(String.join("\n", headerLines));
        System.out.println("\n");
    }
}

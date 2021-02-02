package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> header;
    private final Map<String, String> parameters;

    private HttpRequest(HttpMethod httpMethod, String path, Map<String, String> header, Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.header = header;
        this.parameters = parameters;
    }

    public static HttpRequest from(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String[] requestLine = readRequestLine(br);
            HttpMethod httpMethod= getHttpMethodFromRequestLine(requestLine);
            String path = getPathFromRequestLine(requestLine);

            List<String> requestHeader = readRequestHeader(br);
            Map<String, String> header = getHeaderFromRequestHeader(requestHeader);

            int contentLength = header.containsKey("Content-Length") ? Integer.parseInt(header.get("Content-Length")) : 0;
            String requestBody = readRequestBody(br, contentLength);
            Map<String, String> parameters = getParametersFromRequestLine(requestLine);
            parameters.putAll(getParametersFromRequestBody(requestBody));
            return new HttpRequest(httpMethod, path, header, parameters);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String[] readRequestLine(BufferedReader br) throws IOException {
        return br.readLine().split(" ");
    }

    private static List<String> readRequestHeader(BufferedReader br) throws IOException {
        List<String> header = new ArrayList<>();
        String line = br.readLine();
        while (line != null && !"".equals(line)) {
            header.add(line);
            line = br.readLine();
        }
        return header;
    }

    private static String readRequestBody(BufferedReader br, int contentLength) throws IOException {
        return IOUtils.readData(br, contentLength);
    }

    private static HttpMethod getHttpMethodFromRequestLine(String[] requestLine) {
        return HttpMethod.valueOf(requestLine[0]);
    }

    private static String getPathFromRequestLine(String[] requestLine) {
        String path = requestLine[1];
        int idx = path.indexOf('?');
        if (idx != -1) {
            path = path.substring(0, idx);
        }
        return path.equals("/") ? "/index.html" : path;
    }

    private static Map<String, String> getHeaderFromRequestHeader(List<String> requestHeader) {
        Map<String, String> header = new HashMap<>();
        for(String line: requestHeader) {
            String[] data = line.split(": ");
            header.put(data[0], data[1]);
        }
        return header;
    }

    private static Map<String, String> getParametersFromRequestLine(String[] requestLine) {
        Map<String, String> parameters = new HashMap<>();
        String line = requestLine[1];
        int idx = line.indexOf('?');
        if (idx != -1) {
            String[] queries = line.substring(idx+1).split("&");
            parameters = getParametersFromQueries(queries);
        }
        return parameters;
    }
    
    private static Map<String, String> getParametersFromQueries(String[] queries) {
        Map<String, String> parameters = new HashMap<>();
        for(String query : queries) {
            String[] data = query.split("=");
            parameters.put(data[0], data[1]);
        }
        return parameters;
    }

    private static Map<String, String> getParametersFromRequestBody(String requestBody) {
        if(requestBody.length() == 0) {
            return new HashMap<>();
        }
        String[] queries = requestBody.split("&");
        return getParametersFromQueries(queries);
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
            throw new RuntimeException(message);
        }

        return header.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void logRequestHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========= Request Header =========\n")
                .append(httpMethod.toString()).append(" ")
                .append(path).append(" ")
                .append(RequestHandler.HTTP_VERSION_NAME).append("\n");
        for (Map.Entry<String, String> entries : header.entrySet()) {
            sb.append(entries.getKey())
                    .append(": ").append(entries.getValue()).append("\n");
        }
        sb.append("----------------------------------");
        String message = sb.toString();
        logger.debug(message);
    }
}

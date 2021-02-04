package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String NOT_EXIST_MESSAGE = "(이)가 존재하지 않습니다.";
    private static final String CONTENT_LENGTH = "Content-Length";

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
            List<String> requestLine = readRequestLine(br);
            HttpMethod httpMethod = getHttpMethodFromRequestLine(requestLine);
            String path = getPathFromRequestLine(requestLine);
            Map<String, String> parameters = getParametersFromRequestLine(requestLine);

            List<String> requestHeader = readRequestHeader(br);
            Map<String, String> header = getHeaderFromRequestHeader(requestHeader);

            int contentLength = header.containsKey(CONTENT_LENGTH) ? Integer.parseInt(header.get(CONTENT_LENGTH)) : 0;
            String requestBody = readRequestBody(br, contentLength);
            parameters.putAll(getParametersFromRequestBody(requestBody));
            return new HttpRequest(httpMethod, path, header, parameters);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static List<String> readRequestLine(BufferedReader br) throws IOException {
        return new ArrayList(Arrays.asList(br.readLine().split(" ")));
    }

    private static HttpMethod getHttpMethodFromRequestLine(List<String> requestLine) {
        return HttpMethod.valueOf(requestLine.get(0));
    }

    private static String getPathFromRequestLine(List<String> requestLine) {
        String path = requestLine.get(1);
        int idx = path.indexOf('?');
        if (idx != -1) {
            path = path.substring(0, idx);
        }
        return path.equals("/") ? "/index.html" : path;
    }

    private static Map<String, String> getParametersFromRequestLine(List<String> requestLine) {
        Map<String, String> parameters = new HashMap<>();
        String line = requestLine.get(1);
        int idx = line.indexOf('?');
        if (idx != -1) {
            String[] queries = line.substring(idx+1).split("&");
            parameters = getParametersFromQueries(queries);
        }
        return parameters;
    }

    private static List<String> readRequestHeader(BufferedReader br) throws IOException {
        List<String> header = new ArrayList<>();
        String line = br.readLine();
        while (line != null && !line.isEmpty()) {
            header.add(line);
            line = br.readLine();
        }
        return header;
    }

    private static Map<String, String> getHeaderFromRequestHeader(List<String> requestHeader) {
        Map<String, String> header = new HashMap<>();
        for(String line : requestHeader) {
            String[] data = line.split(":(\\s*)");
            header.put(data[0], data[1]);
        }
        return header;
    }

    private static String readRequestBody(BufferedReader br, int contentLength) throws IOException {
        return IOUtils.readData(br, contentLength);
    }

    private static Map<String, String> getParametersFromRequestBody(String requestBody) {
        if(requestBody.length() == 0) {
            return new HashMap<>();
        }
        String[] queries = requestBody.split("&");
        return getParametersFromQueries(queries);
    }

    private static Map<String, String> getParametersFromQueries(String[] queries) {
        Map<String, String> parameters = new HashMap<>();
        for(String query : queries) {
            String[] data = query.split("=");
            parameters.put(data[0], data[1]);
        }
        return parameters;
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        if (!header.containsKey(key)) {
            String message = key + NOT_EXIST_MESSAGE;
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
        sb.append("\n");
        logger.debug(sb.toString());
    }
}

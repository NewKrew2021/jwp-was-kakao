package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.parser.CookieParser;
import webserver.http.parser.FormUrlencodedBodyParser;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod method;
    private String path;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private String body;

    private HttpRequest() {
        parameters = new HashMap<>();
        headers = new HashMap<>();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getBodyInMap() {
        if (!headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
            throw new UnsupportedOperationException("HttpRequest does not support form body");
        }
        String contentType = headers.get(HttpHeaders.CONTENT_TYPE);

        if (ContentType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
            return FormUrlencodedBodyParser.parse(body);
        }

        throw new UnsupportedOperationException("HttpRequest does not support form body, Content-Type=" + contentType);
    }

    public Map<String, String> getCookiesInMap() {
        if (!headers.containsKey(HttpHeaders.COOKIE)) {
            logger.debug("empty cookies");
            return new HashMap<>();
        }

        String cookieString = headers.get(HttpHeaders.COOKIE);
        return CookieParser.parse(cookieString);
    }

    public static class Parser {
        public static HttpRequest parse(InputStream in) throws IOException {
            HttpRequest httpRequest = new HttpRequest();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = br.readLine();

            handleFirstLine(inputLine, httpRequest);
            while(isNotEmpty(inputLine = br.readLine())) {
                handleLine(inputLine, httpRequest);
            }
            readBody(br, httpRequest);

            logger.debug("method={}, path={}", httpRequest.method, httpRequest.path);
            logger.debug("parameters : {}", httpRequest.parameters);
            logger.debug("headers : {}", httpRequest.headers);
            return httpRequest;
        }

        private static boolean isNotEmpty(String line) {
            return (line != null) && (line.length() > 0);
        }

        private static void handleLine(String inputLine, HttpRequest httpRequest) {
            String[] headerLine = inputLine.split(":", 2);
            httpRequest.headers.put(headerLine[0].trim(), headerLine[1].trim());
        }

        private static void handleFirstLine(String firstLine, HttpRequest httpRequest) {
            try {
                String urlDecoded = URLDecoder.decode(firstLine, "utf-8");
                String[] token = urlDecoded.split(" ");
                httpRequest.method = HttpMethod.valueOf(token[0].trim());
                parsePathAndParameters(token[1], httpRequest);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException : " + e.getMessage());
            }
        }

        private static void parsePathAndParameters(String pathAndParams, HttpRequest httpRequest) {
            String[] tokens = pathAndParams.split("[?&]");
            httpRequest.path = tokens[0];
            Stream.of(tokens).skip(1)
                    .map(param -> param.split("="))
                    .forEach(p -> httpRequest.parameters.put(p[0], p[1]));
        }

        private static void readBody(BufferedReader br, HttpRequest httpRequest) throws IOException {
            if (!httpRequest.headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                return;
            }
            int contentLength = Integer.parseInt(httpRequest.headers.get(HttpHeaders.CONTENT_LENGTH));
            httpRequest.body = IOUtils.readData(br, contentLength);
        }
    }
}

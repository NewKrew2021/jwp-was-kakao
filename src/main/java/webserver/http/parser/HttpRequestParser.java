package webserver.http.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    private static final String HEADER_CONTENT_LENGTH = "Content-Length";

    private HttpMethod method = null;
    private String path = null;
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public HttpRequestParser() { }

    public HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String inputLine = br.readLine();

        while (inputLine != null && inputLine.length() > 0) {
            handleLine(inputLine);
            inputLine = br.readLine();
        }

        readBody(br);

        logger.debug("method={}, path={}", method, path);
        logger.debug("parameters : {}", parameters);
        logger.debug("headers : {}", headers);
        return new HttpRequest(method, path, parameters, headers, body);
    }

    private void handleLine(String inputLine) {
        if (isFirstLine()) {
            handleFirstLine(inputLine);
            return;
        }
        String[] headerLine = inputLine.split(":", 2);
        headers.put(headerLine[0].trim(), headerLine[1].trim());
    }

    private boolean isFirstLine() {
        return method == null && path == null && parameters.isEmpty();
    }

    private void handleFirstLine(String firstLine) {
        try {
            String urlDecoded = URLDecoder.decode(firstLine, "utf-8");
            String[] token = urlDecoded.split(" ");
            method = HttpMethod.valueOf(token[0].trim());
            parsePathAndParameters(token[1]);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException : " + e.getMessage());
        }
    }

    private void parsePathAndParameters(String pathAndParams) {
        String[] tokens = pathAndParams.split("[?&]");
        path = tokens[0];
        Stream.of(tokens).skip(1)
                .map(param -> param.split("="))
                .forEach(p -> parameters.put(p[0], p[1]));
    }

    private void readBody(BufferedReader br) throws IOException {
        if (!headers.containsKey(HEADER_CONTENT_LENGTH)) {
            return;
        }
        int contentLength = Integer.parseInt(headers.get(HEADER_CONTENT_LENGTH));
        body = IOUtils.readData(br, contentLength);
    }
}

package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    private HttpMethod method = null;
    private String path = null;
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    public HttpRequestParser() { }

    public HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String inputLine = br.readLine();

        while (inputLine != null && inputLine.length() > 0) {
            handleLine(inputLine);
            inputLine = br.readLine();
        }

        logger.debug("method={}, path={}", method, path);
        logger.debug("parameters : {}", parameters);
        logger.debug("headers : {}", headers);
        return new HttpRequest(method, path, parameters, headers);
    }

    private void handleLine(String inputLine) throws UnsupportedEncodingException {
        if (isFirstLine()) {
            String urlDecoded = URLDecoder.decode(inputLine, "utf-8");
            String[] firstLine = urlDecoded.split(" ");
            method = HttpMethod.valueOf(firstLine[0].trim());
            parsePathAndParameters(firstLine[1]);
            return;
        }
        String[] headerLine = inputLine.split(":", 2);
        headers.put(headerLine[0].trim(), headerLine[1].trim());
    }

    private boolean isFirstLine() {
        return method == null && path == null && parameters.isEmpty();
    }

    private void parsePathAndParameters(String pathAndParams) {
        String[] tokens = pathAndParams.split("[?&]");
        path = tokens[0];
        Stream.of(tokens).skip(1)
                .map(param -> param.split("="))
                .forEach(p -> parameters.put(p[0], p[1]));
    }
}

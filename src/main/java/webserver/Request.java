package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int METHOD = 1;
    private static final int REQUEST_URI = 2;
    private static final int PARAMETER = 3;
    private static final int HTTP_VERSION = 4;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private HttpMethod method;
    private URI uri;
    private String httpVersion;

    private Request(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line = br.readLine();
        parseRequestLine(line);
        initializeHeader(br);
        initializeParameter(br);
    }

    private void initializeParameter(BufferedReader br) throws IOException {
        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            mapParameter(body);
            logger.debug("body : {} ", body);
        }
    }

    private void initializeHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        logger.debug("request line : {}", line);
        while (!"".equals(line) && line != null) {
            line = br.readLine();
            mapHeader(line);
            logger.debug("header : {}", line);
        }
    }

    private void parseRequestLine(String requestLineString) throws UnsupportedEncodingException {
        Pattern requestLinePattern = Pattern.compile("(GET|POST)\\s+([^?]+)(?:\\?(.+))?\\s+(HTTP/.+)");
        Matcher matcher = requestLinePattern.matcher(requestLineString);
        matcher.find();
        method = HttpMethod.valueOf(matcher.group(METHOD));
        uri = URI.create(matcher.group(REQUEST_URI));
        httpVersion = matcher.group(HTTP_VERSION);

        if (matcher.group(PARAMETER) != null) {
            mapParameter(matcher.group(PARAMETER));
        }
    }

    private void mapParameter(String body) throws UnsupportedEncodingException {
        String[] tokens = body.split("&");
        for (String token : tokens) {
            String[] values = token.split("=");
            parameters.put(values[KEY].trim(), URLDecoder.decode(values[VALUE],
                    java.nio.charset.StandardCharsets.UTF_8.toString()).trim());
        }
    }

    private void mapHeader(String line) {
        if (!line.equals("")) {
            String[] splitLine = line.split(":");
            headers.put(splitLine[KEY].trim(), splitLine[VALUE].trim());
        }
    }

    public static Request of(InputStream in) throws Exception {
        return new Request(in);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri.toString();
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public String getParameter(String parameter) {
        return this.parameters.get(parameter);
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }
}

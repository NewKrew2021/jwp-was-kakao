package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int REMAIN_STRING_INDEX = 1;
    private static final int PARAMETER_INDEX = 0;
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private String method;
    private String uri;
    private BufferedReader br;

    private Request(InputStream in) throws Exception {
        br = new BufferedReader(new InputStreamReader(in));
        initializeHeader();
        initializeRequest();
    }

    private void initializeHeader() throws IOException {
        String line = br.readLine();
        headers.put("Request-Line", getRequestLine(line));

        logger.debug("request line : {}", line);
        while (!"".equals(line) && line != null) {
            line = br.readLine();
            mapHeader(line);
            logger.debug("header : {}", line);
        }
    }

    private String getRequestLine(String line) throws UnsupportedEncodingException {
        if (line.contains("?")) {
            String[] tokens = line.split("\\?");
            mapParameter(tokens[REMAIN_STRING_INDEX].split(" ")[PARAMETER_INDEX]);
            line = tokens[REQUEST_LINE_INDEX];
        }
        return line;
    }

    private void initializeRequest() throws IOException {
        String[] requestLine = headers.get("Request-Line").split(" ");
        this.method = requestLine[METHOD_INDEX];
        this.uri = requestLine[URI_INDEX];
        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            mapParameter(body);
            logger.debug("body : {} ", body);
        }
    }

    private void mapParameter(String body) throws UnsupportedEncodingException {
        String[] tokens = body.split("&");
        for (String token : tokens) {
            String[] values = token.split("=");
            parameters.put(values[KEY_INDEX].trim(), URLDecoder.decode(values[VALUE_INDEX],
                    java.nio.charset.StandardCharsets.UTF_8.toString()).trim());
        }
    }

    private void mapHeader(String line) {
        if (!line.equals("")) {
            String[] splitLine = line.split(":");
            headers.put(splitLine[KEY_INDEX].trim(), splitLine[VALUE_INDEX].trim());
        }
    }

    public static Request of(InputStream in) throws Exception {
        return new Request(in);
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
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

package webserver;

import exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.ParseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final int METHOD_INDEX = 1;
    private static final int REQUEST_URI_INDEX = 2;
    public static final int PARAMETERS_INDEX = 1;
    public static final int URI_INDEX = 0;

    private Pattern requestLinePattern = Pattern.compile("(GET|POST) (.+) (.+)");
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private String method;
    private String uri;
    private BufferedReader br;
    private List<Cookie> cookies = new ArrayList<>();

    private Request(InputStream in) throws Exception {
        br = new BufferedReader(new InputStreamReader(in));
        initializeHeader();
        initializeRequest();
    }

    private void initializeHeader() throws Exception {
        String line = br.readLine();
        logger.debug("request line : {}", line);
        parseRequestLine(line);

        while (!"".equals(line) && line != null) {
            line = br.readLine();
            mapHeader(line);
            logger.debug("header : {}", line);
        }
    }

    private void parseRequestLine(String line) throws Exception {
        Matcher matches = requestLinePattern.matcher(line);
        if(!matches.find()) {
            throw new InvalidRequestException();
        }
        this.method = matches.group(METHOD_INDEX);
        this.uri = matches.group(REQUEST_URI_INDEX);
        if (uri.contains("?")) {
            String[] tokens = uri.split("\\?");
            mapParameter(tokens[PARAMETERS_INDEX]);
            uri = tokens[URI_INDEX];
        }
    }

    private void initializeRequest() throws IOException {
        if (headers.containsKey("Content-Length")) {
            String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
            mapParameter(body);
            logger.debug("body : {} ", body);
        }
    }

    private void mapParameter(String text) throws UnsupportedEncodingException {
        for (Map.Entry<String, String> elem : ParseUtils.parseParametersByAmpersand(text).entrySet()) {
            parameters.put(elem.getKey(), elem.getValue());
        }
    }

    private void mapHeader(String text) {
        if (text.equals("")) {
            return;
        }
        Map.Entry<String, String> elem = ParseUtils.parseParametersByColon(text);
        if(elem.getKey().equals("Cookie")) {
            addCookies(elem.getValue());
            return;
        }
        headers.put(elem.getKey(), elem.getValue());
    }

    private void addCookies(String text) {
        for (String token : ParseUtils.parseParametersBySemicolon(text)) {
            logger.debug("tokenized cookie : {} ", token);
            cookies.add(new Cookie(token));
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

    private Cookie getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.getKey().equals(name))
                .findAny()
                .orElseGet(() -> null);
    }

    public Session getSession() {
        Cookie sessionCookie = getCookie("sessionId");
        if(sessionCookie == null) {
            return null;
        }
        return SessionStorage.getSession(sessionCookie.getValue());
    }
}

package webserver.domain;

import annotation.web.RequestMethod;
import com.google.common.collect.Maps;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

public class HttpRequest {
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "\\?";
    private static final String COLON = ":";
    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final String BLANK = "";
    private static final String ZERO = "0";
    private static final String CONTENT_LENGTH = "Content-Length";
    private final RequestMethod requestMethod;
    private final String path;
    private final String httpVersion;
    private final Map<String, String> headers = Maps.newHashMap();
    private final Map<String, String> parameters = Maps.newHashMap();

    public HttpRequest(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = getNextLine(br);
        String[] token = line.split(SPACE);

        RequestHandler.logger.debug(line);

        checkValidRequest(token);

        requestMethod = RequestMethod.of(token[0]);
        String[] uri = token[1].split(QUESTION_MARK);
        String queryString = (uri.length > 1) ? uri[1] : BLANK;
        path = uri[0];
        httpVersion = token[2];

        makeHeaders(br);
        makeParameters(queryString);
        makeParameters(getBodyData(br));
    }

    private void checkValidRequest(String[] token) {
        if (token.length < 3) {
            throw new IllegalArgumentException("Incorrect request");
        }
    }

    private void makeHeaders(BufferedReader br) {
        for (String line = getNextLine(br); !"".equals(line) && line != null; line = getNextLine(br)) {
            RequestHandler.logger.debug(line);
            String[] tokens = line.split(COLON);
            headers.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    private void makeParameters(String line) {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        Stream.of(line.split(AMPERSAND))
                .forEach(pairValue -> {
                    String[] pair = pairValue.split(EQUAL_SIGN);
                    try {
                        parameters.put(pair[0], URLDecoder.decode(pair[1], StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
    }

    private String getNextLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String getBodyData(BufferedReader br) {
        try {
            int contentLength = Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH, ZERO));
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String getMethod() {
        return requestMethod.toString();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return headers.getOrDefault(header, BLANK);
    }

    public String getParameter(String parameter) {
        return parameters.getOrDefault(parameter, BLANK);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }
}

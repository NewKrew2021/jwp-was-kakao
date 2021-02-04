package webserver.http;

import annotation.web.RequestMethod;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final int FIRST_LINE_CONTENTS_COUNT = 3;
    private static final int NO_CONTENT = 0;
    private final RequestMethod requestMethod;
    private final String path;
    private final String httpVersion;
    private final String rawQueryString;
    private final Headers headers = new Headers();
    private final Parameters parameters = new Parameters();

    public HttpRequest(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] methodPathVersion = getNextLine(br).split(" ");

        checkFirstLineOfRequestIsCorrect(methodPathVersion);

        String[] uri = methodPathVersion[1].split("\\?");

        requestMethod = RequestMethod.of(methodPathVersion[0]);
        path = uri[0];
        rawQueryString = getQueryStringIfExists(uri);
        httpVersion = methodPathVersion[2];

        makeHeaders(br);
        makeParameters(rawQueryString);
        makeParameters(getBodyData(br));
    }

    public String getMethod() {
        return requestMethod.toString();
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    private String getQueryStringIfExists(String[] uri) {
        return (uri.length > 1) ? uri[1] : "";
    }

    private void makeHeaders(BufferedReader br) {
        for (String line = getNextLine(br); !"".equals(line) && line != null; line = getNextLine(br)) {
            headers.saveHeader(line);
        }
    }

    private void makeParameters(String line) {
        parameters.saveParameters(line.split("&"));
    }

    private String getBodyData(BufferedReader br) {
        try {
            return IOUtils.readData(br, getContentLength());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nError while reading body data");
        }
    }

    private int getContentLength() {
        try {
            return Integer.parseInt(headers.get("Content-Length"));
        } catch (NumberFormatException ignored) {
            return NO_CONTENT;
        }
    }

    private String getNextLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nError while reading from input stream");
        }
    }

    private void checkFirstLineOfRequestIsCorrect(String[] token) {
        if (token.length != FIRST_LINE_CONTENTS_COUNT) {
            throw new IllegalArgumentException("Incorrect request");
        }
    }
}

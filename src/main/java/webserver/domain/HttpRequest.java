package webserver.domain;

import annotation.web.RequestMethod;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final String SPACE = " ";
    private static final String BLANK = "";
    private static final String ZERO = "0";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int URI_INDEX = 1;
    private static final int REQUEST_METHOD_INDEX = 0;
    private static final int HTTP_VERSION_INDEX = 2;
    private final RequestMethod requestMethod;
    private final URI uri;
    private final HttpVersion httpVersion;
    private final Headers headers;
    private final RequestBody requestBody;

    public HttpRequest(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = getNextLine(br);
        String[] token = line.split(SPACE);

        checkValidRequest(token);

        requestMethod = RequestMethod.of(token[REQUEST_METHOD_INDEX]);
        uri = new URI(token[URI_INDEX]);
        httpVersion = new HttpVersion(token[HTTP_VERSION_INDEX]);
        headers = new Headers(br);

        requestBody = new RequestBody(new Parameters(uri.getQueryString(), getBodyData(br)));
    }

    private void checkValidRequest(String[] token) {
        if (token.length < 3) {
            throw new IllegalArgumentException("Incorrect request");
        }
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
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH, ZERO));
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String getMethod() {
        return requestMethod.toString();
    }

    public String getPath() {
        return uri.getPath();
    }

    public String getHeader(String header) {
        return headers.get(header, BLANK);
    }

    public String getParameter(String parameter) {
        return requestBody.getParameter(parameter, BLANK);
    }

    public String getHttpVersion() {
        return httpVersion.getHttpVersion();
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }
}

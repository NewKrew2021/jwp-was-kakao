package domain;

import annotation.web.RequestMethod;
import exception.ExceptionHandler;
import exception.HttpRequestFormatException;
import exception.HttpRequestInputException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpRequest {
    private static final String STATUS_DELIMITER = " ";
    private static final String HEADER_DELIMITER = "";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_FOR_REGEX = "\\?";

    private RequestMethod method;
    private String path;
    private HttpParameter httpParameter;
    private HttpHeader httpHeader;

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            setMethodAndPath(br);
            setHeader(br);
            setParameter(br);
        } catch (IOException e) {
            ExceptionHandler.getInstance().handle(new HttpRequestInputException());
        } catch (NullPointerException e) {
            ExceptionHandler.getInstance().handle(new HttpRequestFormatException());
        }

    }

    private void setMethodAndPath(BufferedReader br) throws IOException {
        String line = IOUtils.readLine(br);
        String[] parsed = line.split(STATUS_DELIMITER);
        method = RequestMethod.of(parsed[0]);
        path = parsed[1];
    }

    private void setParameter(BufferedReader br) throws IOException {
        if (getMethod().equals(RequestMethod.GET) && path.contains(PATH_QUERY_DELIMITER)) {
            String[] parsed = path.split(PATH_QUERY_DELIMITER_FOR_REGEX);
            path = parsed[0];
            httpParameter = new HttpParameter(parsed[1]);
        }
        if (getMethod().equals(RequestMethod.POST)) {
            int bodySize = Integer.parseInt(httpHeader.getHeader(HttpHeader.HEADER_CONTENT_LENGTH));
            httpParameter = new HttpParameter(IOUtils.readData(br, bodySize));
        }
    }

    private void setHeader(BufferedReader br) throws IOException {
        httpHeader = new HttpHeader(IOUtils.readUntilDelimiter(br, HEADER_DELIMITER));
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String parameter) {
        return httpParameter.get(parameter);
    }

    public String getCookie(String key) {
        return httpHeader.getCookie(key);
    }

    public boolean isEmpty() {
        return path == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HttpRequest\n").append("Method = ").append(method).append("\nPath = ").append(path).append("\n");
        return sb.toString();
    }
}

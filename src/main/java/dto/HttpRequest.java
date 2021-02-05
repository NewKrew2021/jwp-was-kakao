package dto;

import dto.request.RequestHeaders;
import dto.request.RequestMethod;
import dto.request.RequestParams;
import dto.request.RequestPath;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    public static final int FIRST_LINE = 0;
    public static final int METHOD = 0;
    public static final int URL = 1;
    public static final int PATH = 0;
    public static final int QUERY = 1;

    private RequestMethod method;
    private RequestPath path;
    private final RequestParams params = new RequestParams();
    private RequestHeaders headers;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        setRequest(IOUtils.readDate(br));

        if (getContentLength() != 0) {
            setBody(IOUtils.readData(br, getContentLength()));
        }
    }


    private void setRequest(String message) {
        String[] lines = message.split("\n");
        String[] firstLineTokens = lines[FIRST_LINE].split(" ");

        method = RequestMethod.of(firstLineTokens[METHOD]);

        String[] url = firstLineTokens[URL].split("\\?");
        path = new RequestPath(url[PATH]);
        if (containsQuery(url)) {
            params.putBy(url[QUERY]);
        }

        headers = new RequestHeaders(lines);
    }

    private boolean containsQuery(String[] url) {
        return url.length != 1;
    }

    private void setBody(String body) {
        params.putBy(body);
    }

    public String getMethod() {
        return method.getMethod();
    }

    public String getPath() {
        return path.getPath();
    }

    private int getContentLength() {
        if (headers.containsKey("Content-Length")) {
            return Integer.parseInt(headers.get("Content-Length"));
        }

        return 0;
    }

    public String getCookie() {
        if (headers.containsKey("Cookie")) {
            return headers.get("Cookie");
        }
        return "logined=false";
    }

    public String getHeader(String param) {
        if (headers.containsKey(param)) {
            return headers.get(param);
        }
        return "";
    }

    public String getParameter(String key) {
        if (params.containsKey(key)) {
            return params.get(key);
        }
        return "";
    }
}

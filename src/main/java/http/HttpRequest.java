package http;

import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpHeader httpHeader;
    private HttpBody httpBody;
    private HttpParameters httpParameters = new HttpParameters();


    public HttpRequest(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            httpHeader = new HttpHeader(bufferedReader, httpParameters);
            httpBody = new HttpBody(bufferedReader, httpHeader.getHeaderByKey("Content-Length"), httpParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HttpMethod getMethod() {
        return httpHeader.getHttpMethod();
    }

    public String getPath() {
        return httpHeader.getPath();
    }

    public String getHeader(String key) {
        return httpHeader.getHeaderByKey(key);
    }

    public String getParameter(String key) {
        return httpParameters.getParameterByKey(key);
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }
}

package request;

import annotation.web.RequestMethod;
import exceptions.HeaderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private static final String COOKIE = "Cookie";
    private static final String COOKIE_TRUE_VALUE = "logined=true";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private RequestUri requestUri;
    private RequestHeader requestHeader;
    private RequestBody requestBody;


    private HttpRequest(RequestUri requestUri, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestUri = requestUri;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String uriLine = br.readLine();
        logger.debug(uriLine);
        RequestUri requestUri = RequestUri.from(uriLine);
        RequestHeader requestHeader = RequestHeader.of(br, logger);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());
        return new HttpRequest(requestUri, requestHeader, requestBody);
    }

    public String getParameter(String key) {
        if (requestBody.getBodyValue(key).isPresent()) {
            return requestBody.getBodyValue(key).get();
        }
        if (requestUri.getUriValue(key).isPresent()) {
            return requestUri.getUriValue(key).get();
        }
        return null;
    }

    public String getHeader(String key) {
        if (requestHeader.getHeaderValue(key).isPresent()) {
            return requestHeader.getHeaderValue(key).get();
        }
        throw new HeaderNotFoundException();
    }

    public RequestMethod getMethod() {
        return requestUri.getRequestMethod();
    }

    public String getPath() {
        return requestUri.getPath();
    }

    public Map<String, String> getBody() {
        return requestBody.getBody();
    }


    public boolean isLogined() {
        Optional<String> cookieValue = requestHeader.getHeaderValue(COOKIE);
        return cookieValue.isPresent() && cookieValue.get().equals(COOKIE_TRUE_VALUE);
    }

}

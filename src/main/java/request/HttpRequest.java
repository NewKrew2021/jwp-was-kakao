package request;

import annotation.web.RequestMethod;
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

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    RequestUri requestUri;
    RequestHeader requestHeader;
    RequestBody requestBody;

    private HttpRequest(RequestUri requestUri, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestUri = requestUri;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    static public HttpRequest from(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String uriLine = br.readLine();
        logger.debug(uriLine);
        RequestUri requestUri = RequestUri.from(uriLine);
        RequestHeader requestHeader = RequestHeader.of(br, logger);
        RequestBody requestBody = RequestBody.of(br, requestHeader);
        return new HttpRequest(requestUri, requestHeader, requestBody);
    }

    public String getParameter(String key) {
        if(requestBody.getBodyValue(key).isPresent()) {
            return requestBody.getBodyValue(key).get();
        }
        if(requestUri.getUriValue(key).isPresent()) {
            return requestUri.getUriValue(key).get();
        }
        throw new RuntimeException("커스텀 익셉션 필요. 벨류를 찾을 수 없음");
    }

    public Optional<String> getHeader(String key) {
        return requestHeader.getHeaderValue(key);
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


}

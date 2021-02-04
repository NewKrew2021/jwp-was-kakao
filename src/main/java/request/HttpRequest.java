package request;

import annotation.web.RequestMethod;
import exceptions.HeaderNotFoundException;
import exceptions.ParameterNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.SessionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String SESSION_ID = "sessionId";
    public static final String LOGINED = "logined";
    public static final String TRUE = "true";

    private RequestUri requestUri;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpSession httpSession;


    private HttpRequest(RequestUri requestUri, RequestHeader requestHeader, RequestBody requestBody, HttpSession httpSession) {
        this.requestUri = requestUri;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpSession = httpSession;
    }

    public static HttpRequest from(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String uriLine = br.readLine();
        logger.debug(uriLine);
        RequestUri requestUri = RequestUri.from(uriLine);
        RequestHeader requestHeader = RequestHeader.of(br, logger);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());
        HttpSession httpSession = setSession(requestHeader);
        return new HttpRequest(requestUri, requestHeader, requestBody, httpSession);
    }

    private static HttpSession setSession(RequestHeader requestHeader) {
        HttpSession httpSession;
        String sessionId = requestHeader.getCookies()
                .stream()
                .filter(cookie -> cookie.getName().equals(SESSION_ID))
                .map(Cookie::getValue).findFirst()
                .orElse(null);
        if (SessionHandler.getSession(sessionId).isPresent()) {
            httpSession = SessionHandler.getSession(sessionId).get();
            setCookieOnSession(httpSession, requestHeader.getCookies());
            return httpSession;
        }
        httpSession = SessionHandler.getSession(SessionHandler.createSession()).get();
        setCookieOnSession(httpSession, requestHeader.getCookies());
        return httpSession;
    }

    private static void setCookieOnSession(HttpSession httpSession, List<Cookie> Cookies) {
        Cookies.forEach(cookie -> httpSession.setAttribute(cookie.getName(), cookie.getValue()));
    }

    public String getParameter(String key) {
        if (requestBody.getBodyValue(key).isPresent()) {
            return requestBody.getBodyValue(key).get();
        }
        if (requestUri.getUriValue(key).isPresent()) {
            return requestUri.getUriValue(key).get();
        }
        throw new ParameterNotFoundException();
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
        Optional<Object> loginValue = httpSession.getAttribute(LOGINED);
        return loginValue.isPresent() && loginValue.get().toString().equals(TRUE);
    }

}

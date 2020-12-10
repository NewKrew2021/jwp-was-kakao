package webserver.http;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;

import java.util.*;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.stream.Collectors.*;

public class HttpRequest {
    public static final HttpSessionFactory HTTP_SESSION_FACTORY = new HttpSessionFactory();
    public static final String SESSION_ID = "session_id";
    public static final String COOKIE_HEADER = "Cookie";
    private final HttpMethod method;
    private final String requestURI;
    private final String protocol;
    private Map<String, String> queryParams = Collections.emptyMap();
    private final Map<String, String> headers = new HashMap<>();
    private Map<String, String> entity = Collections.emptyMap();
    private String sessionId;
    private Cookies cookies;

    public HttpRequest(String method, String requestURI, String protocol) {
        this.method = HttpMethod.valueOf(method);
        this.requestURI = requestURI;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void addHeaders(Map<String, String> added) {
        headers.putAll(added);
    }

    public void setEntity(Map<String, String> entity) {
        this.entity = entity;
    }

    public Map<String, String> getEntity() {
        return entity;
    }

    public Cookies getCookies() {
        if (cookies == null) {
            cookies = Optional.ofNullable(getHeaders().get(COOKIE_HEADER))
                    .map(Cookies::new)
                    .orElse(null);
        }
        return cookies;
    }

    public String getParameter(String name) {
        return queryParams.getOrDefault(name, entity.get(name));
    }

    public HttpSession getSession() {
        parseSessionId();

        return getHttpSession();
    }

    private HttpSession getHttpSession() {
        HttpSession httpSession = HTTP_SESSION_FACTORY.getOrCreate(sessionId);
        sessionId = httpSession.getId();
        return httpSession;
    }

    private void parseSessionId() {
        Optional<Cookies> cookies = Optional.ofNullable(getCookies());
        if (sessionId == null) {
            sessionId = cookies.flatMap(cookie -> cookie.findCookieByName(SESSION_ID))
                    .orElse(null);
        }
    }

    public static class Cookies {
        public static final Splitter COOKIE_SPLITTER = Splitter.on("; ");
        public static final String KEY_VALUE_SPLIT = "=";
        private final List<String> cookies;

        public Cookies(String cookie) {
            cookies = Streams.stream(COOKIE_SPLITTER.split(cookie))
                    .map(String::trim)
                    .collect(toList());
        }

        public Map<String, String> asMap() {
            return cookies.stream()
                    .map(cookie -> {
                        String[] cookieToken = cookie.split(KEY_VALUE_SPLIT);
                        return new SimpleEntry<>(cookieToken[0], cookieToken[1]);
                    }).collect(collectingAndThen(
                            toMap(SimpleEntry::getKey, SimpleEntry::getValue),
                            ImmutableMap::copyOf));
        }

        public Optional<String> findCookieByName(String name) {
            return cookies.stream()
                    .filter(cookie -> cookie.startsWith(name))
                    .map(cookie -> cookie.split(KEY_VALUE_SPLIT)[1])
                    .findFirst();
        }

        public boolean contains(String cookie) {
            return cookies.contains(cookie);
        }
    }
}

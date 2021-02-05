package request;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestHeader {
    private static final String SESSION_ID = "sessionId";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_DISTINGUISH_REGEX = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> header;

    private List<Cookie> cookies;

    private RequestHeader(Map<String, String> header, List<Cookie> cookies) {
        this.header = header;
        this.cookies = cookies;
    }

    public static RequestHeader of(BufferedReader br, Logger logger) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        List<Cookie> cookies = new ArrayList<>();
        String tempLine;
        while (!(tempLine = br.readLine()).equals("")) {
            String[] splitTempLine = tempLine.split(HEADER_DISTINGUISH_REGEX);
            String key = splitTempLine[KEY_INDEX].trim();
            String value = splitTempLine[VALUE_INDEX].trim();
            requestHeader.put(key, value);
            addIfCookie(key, value, cookies);
            logger.debug("header: {}", tempLine);
        }
        return new RequestHeader(requestHeader, cookies);
    }

    private static void addIfCookie(String key, String value, List<Cookie> cookies) {
        if (key.equals("Cookie")) {
            cookies.add(Cookie.from(value));
        }
    }

    public Optional<Integer> getContentLength() {
        if (header.containsKey(CONTENT_LENGTH)) {
            return Optional.of(Integer.parseInt(header.get(CONTENT_LENGTH)));
        }
        return Optional.empty();
    }

    public Optional<String> getHeaderValue(String key) {
        if (header.containsKey(key)) {
            return Optional.of(header.get(key));
        }
        return Optional.empty();
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public String getSessionId() {
        return cookies.stream()
                .filter(cookie -> cookie.getName().equals(SESSION_ID))
                .map(Cookie::getValue).findFirst()
                .orElse(null);
    }

}

package webserver;

import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

class RequestParser {
    public static final String HEADER_KEY_VALUE_SPLITTER = ": ";
    public static final String QUERY_SPLITTER = "\\?";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BufferedReader bufferedReader;

    public RequestParser(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public HttpRequest parse() {
        HttpRequest httpRequest = parseRequestLine();

        httpRequest.setHeaderAndCookies(getHeaders());
        httpRequest.setEntity(getEntity(httpRequest.getHeaders().get("Content-Length")));

        return httpRequest;
    }

    private Map<String, String> getEntity(String contentLengthHeader) {
        return getContentLengthFromHeader(contentLengthHeader)
                .map(contentLength -> readEntity(contentLengthHeader))
                .orElse(emptyMap());
    }

    private Map<String, String> readEntity(String contentLengthHeader) {
        String entityString;
        try {
            entityString = IOUtils.readData(bufferedReader, Integer.parseInt(contentLengthHeader));
        } catch (IOException e) {
            throw new RequestReadException(e);
        }

        return new UrlEncodedStringParser(entityString).parse();
    }

    private Optional<Integer> getContentLengthFromHeader(String contentLengthHeader) {
        if (Objects.isNull(contentLengthHeader)) {
            return Optional.empty();
        }

        return Optional.of(Integer.parseInt(contentLengthHeader));
    }

    private HttpRequest parseRequestLine() {
        String requestLine = nextLine();
        logger.debug("requestLine: {}", requestLine);
        String[] requestLineToken = requestLine.split(" ");
        String requestURI = requestLineToken[1];
        String[] requestURIToken = requestURI.split(QUERY_SPLITTER);

        HttpRequest httpRequest = new HttpRequest(requestLineToken[0], requestURIToken[0], requestLineToken[2]);
        if (requestURIToken.length == 2) {
            httpRequest.setQueryParams(new UrlEncodedStringParser(requestURIToken[1]).parse());
        }
        return httpRequest;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = nextLine()).equals("")) {
            logger.debug("headerLine: {}", headerLine);
            String[] header = headerLine.split(HEADER_KEY_VALUE_SPLITTER);
            headers.put(header[0], header[1]);
        }
        return headers;
    }

    private String nextLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RequestReadException(e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    static class UrlEncodedStringParser {
        public static final MapSplitter QUERY_STRING_SPLITTER = Splitter.on("&").withKeyValueSeparator("=");
        private final String queryString;

        public UrlEncodedStringParser(String queryString) {
            this.queryString = queryString;
        }

        public Map<String, String> parse() {
            return QUERY_STRING_SPLITTER.split(queryString)
                    .entrySet().stream()
                    .map(entry -> new SimpleEntry<>(entry.getKey(), decode(entry.getValue())))
                    .collect(collectingAndThen(toMap(
                            SimpleEntry::getKey, SimpleEntry::getValue), ImmutableMap::copyOf));
        }

        private String decode(String s) {
            try {
                return URLDecoder.decode(s, UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

package webserver;

import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.common.collect.ImmutableMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

class RequestParser {
    private final BufferedReader bufferedReader;

    public RequestParser(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public HttpRequest parse() {
        return parseRequestLine();
    }

    private HttpRequest parseRequestLine() {
        String requestLine = nextLine();
        String[] requestLineToken = requestLine.split(" ");
        String requestURI = requestLineToken[1];
        String[] requestURIToken = requestURI.split("\\?");

        HttpRequest httpRequest = new HttpRequest(requestLineToken[0], requestURIToken[0], requestLineToken[2]);
        if (requestURIToken.length == 2) {
            httpRequest.setQueryParams(new QueryStringParser(requestURIToken[1]).parse());
        }
        return httpRequest;
    }

    private String nextLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    static class QueryStringParser {
        public static final MapSplitter QUERY_STRING_SPLITTER = Splitter.on("&").withKeyValueSeparator("=");
        private final String queryString;

        public QueryStringParser(String queryString) {
            this.queryString = queryString;
        }

        public Map<String, String> parse() {
            return QUERY_STRING_SPLITTER.split(queryString) //
                    .entrySet().stream() //
                    .map(entry -> new SimpleEntry<>(entry.getKey(), decode(entry.getValue()))) //
                    .collect(collectingAndThen(toMap( //
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

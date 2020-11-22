package webserver;

import com.google.common.collect.ImmutableMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
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

    static class QueryStringParser {
        private final String queryString;

        public QueryStringParser(String queryString) {
            this.queryString = queryString;
        }

        public Map<String, String> parse() {
            String[] queryStringToken = queryString.split("&");
            return Arrays.stream(queryStringToken) //
                    .map(token -> token.split("=")) //
                    .map(keyValueArray -> {
                        return new String[]{keyValueArray[0], decode(keyValueArray[1])};
                    }) //
                    .collect(collectingAndThen(toMap( //
                            keyValueArray -> keyValueArray[0], //
                            keyValueArray -> keyValueArray[1]), //
                            ImmutableMap::copyOf));
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

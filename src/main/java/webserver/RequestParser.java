package webserver;

import java.io.BufferedReader;
import java.io.IOException;

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
        return new HttpRequest(requestLineToken[0], requestURIToken[0], requestLineToken[2]);
    }

    private String nextLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

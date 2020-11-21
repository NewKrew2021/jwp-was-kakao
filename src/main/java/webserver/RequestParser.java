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
        String[] requestLineSplit = requestLine.split(" ");
        return new HttpRequest(requestLineSplit[0], requestLineSplit[1], requestLineSplit[2]);
    }

    private String nextLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

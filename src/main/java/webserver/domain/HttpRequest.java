package webserver.domain;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpRequest {
    private final HttpHeaders headers;
    private final HttpParameters parameters;
    private HttpMethod method;
    private String body;
    private String path;

    public HttpRequest(InputStream in) throws IOException {
        headers = new HttpHeaders();
        parameters = new HttpParameters();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        readFirstLine(reader);
        readHeaders(reader);
        if (headers.contain(HttpHeader.CONTENT_LENGTH)) {
            body = IOUtils.readData(reader, Integer.parseInt(headers.get(HttpHeader.CONTENT_LENGTH)));
            parameters.parseAndSet(body);
        }
    }

    private void readFirstLine(BufferedReader reader) throws IOException {
        String[] firstLineTokens = reader.readLine().split(" ");
        String requestUrl = firstLineTokens[1];
        method = HttpMethod.valueOf(firstLineTokens[0]);
        parseUrl(requestUrl);
    }

    private void readHeaders(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (isValid(line)) {
            String[] header = line.split(": ");
            headers.add(header[0], header[1]);
            line = reader.readLine();
        }
    }

    private boolean isValid(String line) {
        return (line != null) && !"".equals(line);
    }

    private void parseUrl(String requestUrl) {
        if (requestUrl.indexOf('?') == -1) {
            path = requestUrl;
            return;
        }
        String[] urlToken = requestUrl.split("\\?");
        path = urlToken[0];
        parameters.parseAndSet(urlToken[1]);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpParameters getParameters() {
        return parameters;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }
}

package view;

import utils.HttpParser;
import utils.IOUtils;
import webserver.HttpMethod;
import webserver.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputView {
    private final BufferedReader br;

    private InputView(InputStream in) {
        this.br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public static InputView from(InputStream in) {
        return new InputView(in);
    }

    public HttpRequest getHttpRequest() throws IOException {

        String line = readLine();
        String[] requestLine = HttpParser.parseRequestLine(line);
        HttpMethod httpMethod = HttpParser.parseHttpMethod(requestLine);
        String path = HttpParser.parsePath(requestLine);

        List<String> lines = readLinesUntilEmptyLine();
        Map<String, String> requestHeader = HttpParser.parseRequestHeader(lines);

        int contentLength = HttpParser.parseContentLength(requestHeader);
        String requestBody = read(contentLength);

        Map<String, String> parameters = HttpParser.parseParametersFromRequestLine(requestLine);
        parameters.putAll(HttpParser.parseParametersFromRequestBody(requestBody));

        return HttpRequest.of(httpMethod, path, requestHeader, parameters);
    }

    private String readLine() throws IOException {
        return br.readLine();
    }

    private List<String> readLinesUntilEmptyLine() throws IOException {
        List<String> lines = new ArrayList<>();
        String line = br.readLine();
        while (line != null && !"".equals(line)) {
            lines.add(line);
            line = br.readLine();
        }
        return lines;
    }

    private String read(int contentLength) throws IOException {
        return IOUtils.readData(br, contentLength);
    }
}

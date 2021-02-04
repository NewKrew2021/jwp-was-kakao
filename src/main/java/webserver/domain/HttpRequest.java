package webserver.domain;

import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final String BODY_SEPARATOR = "&|=";
    private static final String PARAMETER_SEPARATOR = "\\?";
    private static final String FIRST_LINE_SEPARATOR = " ";
    private static final String HEADER_SEPARATOR = ": ";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HTTP_VERSION = "Version";


    private Map<String, String> httpHeader = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private HttpMethod httpMethod;
    private String path;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String line = bufferedReader.readLine();
        parseFirstLine(line);
        parseHeader(bufferedReader);
        parseBody(bufferedReader);
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {
        int bodyLength = Integer.parseInt(httpHeader.getOrDefault(CONTENT_LENGTH, "0"));
        if (bodyLength == 0) {
            return;
        }
        String body = IOUtils.readData(bufferedReader, bodyLength);
        String[] parameters = body.split(BODY_SEPARATOR);
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.put(parameters[i], URLDecoder.decode(parameters[i + 1], DEFAULT_ENCODING));
        }
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (line != null && !line.equals("")) {
            String[] currentLine = line.split(HEADER_SEPARATOR);
            httpHeader.put(currentLine[0], URLDecoder.decode(currentLine[1], DEFAULT_ENCODING));
            line = bufferedReader.readLine();
        }
    }

    private void parseFirstLine(String line) throws UnsupportedEncodingException {
        String[] currentLine = line.split(FIRST_LINE_SEPARATOR);
        httpMethod = HttpMethod.resolve(currentLine[0]);

        String[] pathAndParameter = currentLine[1].split(PARAMETER_SEPARATOR);
        path = pathAndParameter[0];

        if (pathAndParameter.length > 1) {
            makeParameter(pathAndParameter[1].split(BODY_SEPARATOR));
        }

        httpHeader.put(HTTP_VERSION, currentLine[2]);
    }

    private void makeParameter(String[] parameters) throws UnsupportedEncodingException {
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.put(parameters[i], URLDecoder.decode(parameters[i + 1], DEFAULT_ENCODING));
        }
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return httpHeader.getOrDefault(key, "");
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }

}

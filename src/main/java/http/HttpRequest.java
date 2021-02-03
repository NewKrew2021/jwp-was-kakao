package http;

import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpHeader httpHeader;
    private HttpBody httpBody;
    private Map<String, String> parameters = new HashMap<>();
    private HttpMethod httpMethod;
    private String path;
    private String version;

    public HttpRequest(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            String line = bufferedReader.readLine();
            parseFirstLine(line);
            httpHeader = new HttpHeader(bufferedReader);
            httpBody = new HttpBody(bufferedReader, httpHeader.getHeaderByKey("Content-Length"));
            parameters.putAll(httpBody.getBodyParameters());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseFirstLine(String line) throws UnsupportedEncodingException {
        String[] currentLine = line.split(" ");
        httpMethod = HttpMethod.resolve(currentLine[0]);

        String[] pathAndParameter = currentLine[1].split("\\?");
        path = pathAndParameter[0];

        if( pathAndParameter.length > 1 ) {
            makeParameter(pathAndParameter[1].split("=|&"));
        }

        version = currentLine[2];
    }

    private void makeParameter(String[] parameters) throws UnsupportedEncodingException {
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.put( parameters[i] , URLDecoder.decode( parameters[i + 1], "UTF-8" ));
        }
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return httpHeader.getHeaderByKey(key);
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }
}

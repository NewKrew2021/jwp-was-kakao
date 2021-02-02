package webserver;

import org.springframework.http.HttpMethod;
import utils.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {


    private Map<String, String> httpHeader = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private HttpMethod httpMethod;
    private String path;

    public HttpRequest(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            String line = bufferedReader.readLine();
            parseFirstLine(line);
            parseHeader(bufferedReader);
            parseBody(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {
        int bodyLength = Integer.parseInt(httpHeader.getOrDefault("Content-Length" , "0"));

        if( bodyLength == 0 ) {
            return;
        }

        String body = IOUtils.readData(bufferedReader, bodyLength);
        String[] parameters = body.split("&|=");
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.put(parameters[i], URLDecoder.decode( parameters[i + 1], "UTF-8" ));
        }
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while ( line != null && !line.equals("")) {
            String[] currentLine = line.split(": ");
            httpHeader.put(currentLine[0], URLDecoder.decode(currentLine[1],"UTF-8"));
            line = bufferedReader.readLine();
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

        httpHeader.put("Version", currentLine[2]);
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
        return httpHeader.getOrDefault(key, "");
    }

    public String getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }

}

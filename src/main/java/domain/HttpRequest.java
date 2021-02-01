package domain;

import annotation.web.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class HttpRequest {
    private RequestMethod method;
    private String path;
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, String> header = new HashMap<>();

    public HttpRequest(InputStream in) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            parseHeader(bufferedReader.readLine());

            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                String[] split = line.split(": ");
                header.put(split[0], split[1]);
                line = bufferedReader.readLine();
            }

            if (getMethod().equals(RequestMethod.POST)) {
                String body = bufferedReader.readLine();
                parseUserInfo(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public HttpRequest(RequestMethod method, String path, Map<String, String> parameters) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    private void parseHeader(String request) {
        String[] parsed = request.split(" ");
        method = RequestMethod.of(parsed[0]);
        String[] requestPath = parsed[1].split("\\?");
        path = requestPath[0];
        if (requestPath.length == 1) {
            return;
        }
        parseUserInfo(requestPath[1]);
    }

    private void parseUserInfo(String request) {
        Arrays.stream(request.split("&"))
                .forEach(rawParameter -> {
                    String[] split = rawParameter.split("=");
                    parameters.put(split[0], split[1]);
                });
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        if (path.equals("")) {
            return "/index.html";
        }
        return path;
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}

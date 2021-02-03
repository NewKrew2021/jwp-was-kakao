package domain;

import annotation.web.RequestMethod;
import utils.IOUtils;

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
    private Map<String, String> cookies = new HashMap<>();

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

            if(header.containsKey("Cookie")) {
                Arrays.stream(header.get("Cookie").split("; "))
                        .forEach(cookie -> cookies.put(cookie.split("=")[0], cookie.split("=")[1]));
            }

            if (getMethod().equals(RequestMethod.POST)) {
                int bodySize = Integer.parseInt(header.get("Content-Length"));
                String body = IOUtils.readData(bufferedReader, bodySize);
                parseQueryParameter(body);
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
        parseQueryParameter(requestPath[1]);
    }

    private void parseQueryParameter(String request) {
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
        return path;
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public boolean isEmpty() {
        return path == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HttpRequest\n").append("Method = ").append(method).append("\nPath = ").append(path).append("\n");
        if (!parameters.isEmpty()) {
            sb.append("Parameters[\n");
            for (String s : parameters.keySet()) {
                sb.append("\t").append(s).append(" : ").append(parameters.get(s)).append("\n");
            }
            sb.append("]\n");
        }
        if (!header.isEmpty()) {
            sb.append("Header[\n");
            for (String s : header.keySet()) {
                sb.append("\t").append(s).append(" : ").append(header.get(s)).append("\n");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}

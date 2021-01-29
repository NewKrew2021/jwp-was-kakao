package dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestHeader {
    private String method;
    private String path;
    private List<String> parameters;
    private String host;
    private String connection;

    public RequestHeader(String method, String path, List<String> parameters) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        if (path.equals("")) {
            return "/index.html";
        }
        return path;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public boolean hasParameters() {
        return parameters != null;
    }
}

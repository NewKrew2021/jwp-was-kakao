package request;

import annotation.web.RequestMethod;
import exceptions.MethodNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static utils.HttpRequestUtils.*;


public class RequestUri {
    private RequestMethod requestMethod;
    private String path;
    private Map<String, String> params;

    private RequestUri(RequestMethod requestMethod, String path, Map<String, String> params) {
        this.requestMethod = requestMethod;
        this.path = path;
        this.params = params;
    }

    public static RequestUri from(String line) {
        String[] splitLine = line.split(" ");
        Optional<String> queryString = extractParams(splitLine[1]);
        if (queryString.isPresent()) {
            return new RequestUri(getMethodType(splitLine[0]), extractPath(splitLine[1]), requestStringToMap(queryString.get()));
        }
        return new RequestUri(getMethodType(splitLine[0]), extractPath(splitLine[1]), new HashMap<>());
    }

    private static RequestMethod getMethodType(String line) {
        String method = line.split(" ")[0];
        if (method.equals("GET")) {
            return RequestMethod.GET;
        }
        if (method.equals("POST")) {
            return RequestMethod.POST;
        }
        if (method.equals("DELETE")) {
            return RequestMethod.DELETE;
        }
        if (method.equals("PUT")) {
            return RequestMethod.PUT;
        }
        if (method.equals("PATCH")) {
            return RequestMethod.PATCH;
        }
        throw new MethodNotFoundException();
    }

    public Optional<String> getUriValue(String key) {
        if (params.containsKey(key)) {
            return Optional.of(params.get(key));
        }
        return Optional.empty();
    }


    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

}

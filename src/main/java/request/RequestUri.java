package request;

import annotation.web.RequestMethod;
import exceptions.InvalidRequestLineException;
import exceptions.MethodNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static utils.HttpRequestUtils.*;


public class RequestUri {

    private static final int METHOD_INDEX_IN_LINE = 0;
    private static final int URI_INDEX_IN_LINE = 1;
    private static final String REQUEST_LINE_PATTERN = "\\S+ /\\S+ HTTP/1.1\n";

    private RequestMethod requestMethod;
    private String path;
    private Map<String, String> params;

    private RequestUri(RequestMethod requestMethod, String path, Map<String, String> params) {
        this.requestMethod = requestMethod;
        this.path = path;
        this.params = params;
    }

    public static RequestUri from(String line) {
        validateRequestLine(line);
        String[] splitLine = line.split(" ");
        String requestURI = splitLine[URI_INDEX_IN_LINE];
        String requestMethod = splitLine[METHOD_INDEX_IN_LINE];
        Optional<String> queryString = extractParams(requestURI);
        return queryString
                .map(query -> new RequestUri(getMethodType(requestMethod)
                        , extractPath(requestURI)
                        , requestStringToMap(query)))
                .orElseGet(() -> new RequestUri(getMethodType(requestMethod)
                        , extractPath(requestURI)
                        , new HashMap<>()));
    }

    private static void validateRequestLine(String line) {
        if (!Pattern.matches(REQUEST_LINE_PATTERN, line)) {
            throw new InvalidRequestLineException();
        }
    }

    private static RequestMethod getMethodType(String line) {
        String method = line.split(" ")[METHOD_INDEX_IN_LINE];
        try {
            return RequestMethod.valueOf(method);
        } catch (IllegalArgumentException ex) {
            throw new MethodNotFoundException();
        }
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

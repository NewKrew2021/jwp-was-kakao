package dto;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private Map<String, String> query = new HashMap<>();

    public HttpRequest(String message) {
        String[] lines = message.split("\n");
        String[] firstLineTokens = lines[0].split(" ");

        method = firstLineTokens[0];
        String[] url = firstLineTokens[1].split("\\?");
        uri = url[0];
        if(url.length != 1) {
            String[] queries = url[1].split("&");
            for (String q : queries) {
                String key = q.split("=")[0];
                String value = q.split("=")[1];
                query.put(key, value);
            }
        }


    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getQuery() {
        return query;
    }
}

package dto;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> bodyParams = new HashMap<>();

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
                queryParams.put(key, value);
            }
        }

        for(int i = 1; i < lines.length ; i++){
            headers.put(lines[i].split(":")[0], lines[i].split(":")[1].trim());
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public int getContentLength(){
        if(headers.containsKey("Content-Length")){
            return Integer.parseInt(headers.get("Content-Length"));
        }

        return 0;
    }

    public void setBody(String body) {
        String[] queries = body.split("&");
        for (String q : queries) {
            String key = q.split("=")[0];
            String value = q.split("=")[1];
            bodyParams.put(key, value);
        }
    }

    public String getCookie(){
        if(headers.containsKey("Cookie")){
            return headers.get("Cookie");
        }
        return "logined=false";
    }
}

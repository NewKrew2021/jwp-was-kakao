package dto;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();

    private void addHeader(String message) {
        String[] lines = message.split("\n");
        String[] firstLineTokens = lines[0].split(" ");

        method = firstLineTokens[0];

        String[] url = firstLineTokens[1].split("\\?");
        path = url[0];
        if(url.length != 1) {
            String[] queries = url[1].split("&");
            for (String q : queries) {
                String key = q.split("=")[0];
                String value = q.split("=")[1];
                params.put(key, value);
            }
        }

        for(int i = 1; i < lines.length ; i++){
            headers.put(lines[i].split(":")[0], lines[i].split(":")[1].trim());
        }
    }

    public HttpRequest(InputStream in){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lines = "";
            String line;
            while (!"".equals((line = br.readLine()))) {
                lines += line + "\n";
            }
            addHeader(lines);

            if (getContentLength() != 0) {
                setBody(IOUtils.readData(br, getContentLength()));
            }

        } catch (Exception e){

        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    private int getContentLength(){
        if(headers.containsKey("Content-Length")){
            return Integer.parseInt(headers.get("Content-Length"));
        }

        return 0;
    }

    private void setBody(String body) {
        String[] queries = body.split("&");
        for (String q : queries) {
            String key = q.split("=")[0];
            String value = q.split("=")[1];
            params.put(key, value);
        }
    }

    public String getCookie(){
        if(headers.containsKey("Cookie")){
            return headers.get("Cookie");
        }
        return "logined=false";
    }

    public String getHeader(String param){
        if(headers.containsKey(param)){
            return headers.get(param);
        }
        return "";
    }

    public String getParameter(String key) {
        if (params.containsKey(key)) {
            return params.get(key);
        }
        return "";
    }
}

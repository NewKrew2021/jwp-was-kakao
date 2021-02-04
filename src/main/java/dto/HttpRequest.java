package dto;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();


    public HttpRequest(InputStream in) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            setStartLine(line);
            while (!"".equals((line = br.readLine()))) {
                headers.put(line.split(":")[0], line.split(":")[1].trim());
            }

            if (getContentLength() != 0) {
                setBody(IOUtils.readData(br, getContentLength()));
            }

        } catch (IOException e){
            throw new IOException("Http request를 읽는 과정에서 exception이 발생하였습니다.");
        }
    }

    private void setStartLine(String line) {
        String[] firstLineTokens = line.split(" ");

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

package model;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String methodType;
    private final String[] paths;
    private final String path;
    private boolean isLogin;
    private Map<String, String> params = new HashMap<>();

    //get /user/create?userId=mark&password=123&name=123&email=123%40123.com
    public Request(String path) {
        String[] token = path.split(" ");
        methodType = token[0];
        this.path = token[1];
        this.isLogin=false;
        String[] pathToken = token[1].split("\\?");
        paths = pathToken[0].split("/");
        if (pathToken.length > 1) {
            params = parseParams(pathToken[1]);
        }
    }

    public void setParams(String paramString){
        this.params=parseParams(paramString);
    }

    private Map<String, String> parseParams(String paramString) {
        Map<String, String> map = new HashMap<>();
        for (String path : paramString.split("\\&")) {
            String[] temp = path.split("=");
            map.put(temp[0], temp[1]);
        }
        return map;
    }

    public String getMethodType() {
        return methodType;
    }

    public String[] getPaths() {
        return paths;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getPath() {
        return path;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin(){
        return this.isLogin;
    }
}

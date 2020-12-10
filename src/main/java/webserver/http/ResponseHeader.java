package webserver.http;

import webserver.config.ServerConfigConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHeader {

    public static final String HTML = ".html";
    public static final String CSS = ".css";
    private final Map<String, String> header;

    private ResponseHeader(Map<String, String> header) {
        this.header = header;
    }

    public static ResponseHeader create(){
        Map<String, String> header = new HashMap<>();
        return new ResponseHeader(header);
    }

    public static ResponseHeader of(HttpRequest httpRequest){
        Map<String, String> header = new HashMap<>();
        setContentType(httpRequest, header);
        return new ResponseHeader(header);
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setLoginCookie(HttpRequest httpRequest, boolean isLogin){
        Cookies cookies = httpRequest.getHeader().getCookies();
        cookies.add(ServerConfigConstants.LOGIN_COOKIE_KEY, String.valueOf(isLogin));
        addHeader(HttpHeader.SET_COOKIE, ServerConfigConstants.LOGIN_COOKIE_KEY+ HttpHeader.SEPERATOR +String.valueOf(isLogin)+ HttpHeader.COOKIE_PATH);
    }

    public List<String> makeHeader() {
        List<String> response = new ArrayList<>();
        for (String key : this.getHeader().keySet()) {
            response.add(String.format("%s: %s\r\n", key, getHeader().get(key)));
        }
        return response;
    }

    public static void setContentType(HttpRequest httpRequest, Map<String, String> header){
        if(httpRequest.getPath().endsWith(HTML)){
            header.put(HttpHeader.CONTENT_TYPE, ContentType.HTML.toString());
        }else if(httpRequest.getPath().endsWith(CSS)){
            header.put(HttpHeader.CONTENT_TYPE, ContentType.CSS.toString());
        }
    }
}

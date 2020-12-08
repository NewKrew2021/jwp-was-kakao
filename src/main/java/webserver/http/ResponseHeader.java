package webserver.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHeader {

    public static final String HTML = ".html";
    public static final String CSS = ".css";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String LOGIN_COOKIE_KEY = "logined";
    private final Map<String, String> header = new HashMap<>();

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setLoginCookie(HttpRequest httpRequest, boolean isLogin){
        Cookies cookies = httpRequest.getHeader().getCookies();
        cookies.add(LOGIN_COOKIE_KEY, String.valueOf(isLogin));
        addHeader(SET_COOKIE, cookies.toString());
    }

    public List<String> makeHeader() {
        List<String> response = new ArrayList<>();
        for (String key : this.getHeader().keySet()) {
            response.add(String.format("%s: %s\r\n", key, getHeader().get(key)));
        }
        return response;
    }

    public void setContentType(HttpRequest httpRequest){
        if(httpRequest.getPath().endsWith(HTML)){
            this.addHeader("Content-Type", ContentType.HTML.toString());
        }else if(httpRequest.getPath().endsWith(CSS)){
            this.addHeader("Content-Type", ContentType.CSS.toString());
        }
    }
}

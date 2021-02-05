package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cookie {
    private static final String LOGIN_SUCCESS = "true";
    private static final String LOGIN_COOKIE = "logined";
    private static final String SESSION_COOKIE = "sessionId";
    private Map<String, String> cookie;

    public Cookie(HttpRequest httpRequest){
        cookie = new HashMap<>();

        try {
            String userCookie = httpRequest.getHeader("Cookie");
            String[] cookies = userCookie.replaceAll(" ", "").split(";");
            for (String eachCookie : cookies) {
                String[] cookieKeyValue = eachCookie.split("=");
                cookie.put(cookieKeyValue[0], cookieKeyValue[1]);
            }
        } catch (NullPointerException e){
          return;
        }
    }

    public boolean isLogin(){
        if (cookie.size() != 0 && cookie.get(LOGIN_COOKIE).equals(LOGIN_SUCCESS)){
            return true;
        }
        return false;
    }

    public Optional<String> getSession(){
        return Optional.ofNullable(cookie.get(SESSION_COOKIE));
    }

    public Map<String, String> getCookie(){
        return cookie;
    }
}

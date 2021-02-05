package utils;

public class LoginCheckUtils {
    public static boolean isLogin(String loginCookie) {
        try {
            return Boolean.parseBoolean(loginCookie.split("=")[1]);
        } catch (NullPointerException e) {
            return false;
        }
    }
}

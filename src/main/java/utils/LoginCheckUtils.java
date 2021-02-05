package utils;

import java.util.Map;

public class LoginCheckUtils {

    private static final String DELIMITER = ";";
    private static final String LOGINED = "logined";

    public static boolean isLogin(String cookie) {
        try {
            Map<String, String> arguments = QueryStringParserUtils.getParameterMapFromText(cookie, DELIMITER);
            return Boolean.parseBoolean(arguments.get(LOGINED));
        } catch (NullPointerException e) {
            return false;
        }
    }
}

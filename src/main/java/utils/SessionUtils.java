package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionUtils {

    private static Pattern sessionIdPattern = Pattern.compile("SessionId=([^\\ \\;]+)");

    public static String extractSessionId(String cookie) {
        Matcher matcher = sessionIdPattern.matcher(cookie);

        if (matcher.find()) {
            return matcher.group().split("SessionId=")[1];
        }

        return "";
    }
}

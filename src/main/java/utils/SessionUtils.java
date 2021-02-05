package utils;

import exceptions.InvalidSessionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionUtils {

    public static final int SESSION_ID_VALUE = 1;
    private static Pattern sessionIdPattern = Pattern.compile("SessionId=([^\\ \\;]+)");

    public static String extractSessionId(String cookie) {
        Matcher matcher = sessionIdPattern.matcher(cookie);

        if (matcher.find()) {
            return matcher.group().split("SessionId=")[SESSION_ID_VALUE];
        }

        throw new InvalidSessionException("쿠키에서 SessionId를 찾을 수 없습니다.");
    }
}

package utils;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return org.springframework.util.StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !org.springframework.util.StringUtils.isEmpty(str);
    }

}

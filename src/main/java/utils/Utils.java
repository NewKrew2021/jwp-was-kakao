package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Utils {

    // FIXME ???
    public static <T> T defaultIfNull(T v) {
        Class<?> cls = v.getClass();
        
        if (v != null) {
            return v;
        }

        try {
            return (T)cls.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static String decodeUrl(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static boolean isNotEmtpy(String s) {
        return s != null && s.length() > 0;
    }
}

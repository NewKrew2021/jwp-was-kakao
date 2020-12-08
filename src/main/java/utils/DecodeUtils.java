package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DecodeUtils {

    private DecodeUtils() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static String decodeUTF8(String line) {
        return URLDecoder.decode(line, StandardCharsets.UTF_8);
    }
}

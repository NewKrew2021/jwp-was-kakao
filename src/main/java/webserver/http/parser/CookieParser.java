package webserver.http.parser;

import webserver.http.Cookie;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class CookieParser {

    public static Cookie parse(String cookieString) {

        if (cookieString == null) return new Cookie();

        String[] cookies = cookieString.split("; ");

        Map<String, String> map = Arrays.stream(cookies)
                .map(e -> e.split("="))
                .collect(toMap(v -> v[0], v -> v.length > 1 ? v[1] : ""));

        return new Cookie(map);

    }

}

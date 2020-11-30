package webserver.http.utils;

import utils.StringUtils;
import webserver.http.Cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookieParser {

    public List<Cookie> parse(String cookieHeaderValue) {
        if(StringUtils.isEmpty(cookieHeaderValue) ) return new ArrayList<>();

        List<String> kvPairs = Arrays.asList(cookieHeaderValue.split(";"));

        return kvPairs.stream()
                .map(this::createCookie)
                .collect(Collectors.toList());
    }

    private Cookie createCookie(String kvString){
        List<String> parts = Arrays.stream(kvString.split("="))
                .map(String::trim)
                .collect(Collectors.toList());
        return new Cookie(parts.get(0), parts.get(1));
    }

}

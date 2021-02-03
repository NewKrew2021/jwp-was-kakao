package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private final Map<String, String> cookies;

    public Cookies(String header) {
        cookies = new HashMap<>();
        if (header != null) {
            Arrays.asList(header.split(";"))
                    .forEach(cookie -> {
                        List<String> split = Arrays.stream(cookie.split("="))
                                .map(String::trim)
                                .collect(Collectors.toList());
                        cookies.put(split.get(0), split.get(1));
                    });
        }
    }

    public String getValueOf(String argument) {
        if (cookies.get(argument) == null) {
            return "false";
        }
        return cookies.get(argument);
    }

    @Override
    public String toString() {
        return "Cookies{" +
                "cookies=" + cookies +
                '}';
    }
}

package webserver;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public class Cookie {

    private static final String REGEX_COLON = ":";
    private static final String REGEX_BLANK = " ";
    private static final String REGEX_EQUALS = "=";

    private static final String COOKIE_LOGINED_FILED = "logined";
    private static final boolean COOKIE_LOGINED_DEFAULT = false;

    private final boolean logined;

    private Cookie(boolean logined) {
        this.logined = logined;
    }

    public static Cookie of(String header) {
        if (StringUtils.isEmpty(header)) {
            return new Cookie(COOKIE_LOGINED_DEFAULT);
        }

        String cookieValue = header.split(REGEX_COLON)[1];
        boolean logined = Arrays.stream(cookieValue.split(REGEX_BLANK))
                .filter(value -> value.startsWith(COOKIE_LOGINED_FILED))
                .map(v -> v.split(REGEX_EQUALS)[1])
                .findAny()
                .map(Boolean::valueOf)
                .orElse(COOKIE_LOGINED_DEFAULT);

        return new Cookie(logined);
    }

    public boolean isLogined() {
        return logined;
    }

}

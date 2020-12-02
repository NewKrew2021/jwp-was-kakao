package webserver.http.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.stream.Stream;

public class CookieParser {

    public static void parse(String raw, Map<String, String> output) {
        try {
            String urlDecoded = URLDecoder.decode(raw, "utf-8");
            String[] tokens = urlDecoded.split("; *");
            Stream.of(tokens)
                    .map(param -> param.split("="))
                    .forEach(p -> output.put(p[0], p[1]));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException : " + e.getMessage());
        }
    }
}

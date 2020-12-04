package webserver.http.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormUrlencodedBodyParser {

    public static Map<String, String> parse(String raw) {
        try {
            String urlDecoded = URLDecoder.decode(raw, "utf-8");
            String[] tokens = urlDecoded.split("&");
            return Stream.of(tokens)
                    .map(param -> param.split("="))
                    .collect(Collectors.toMap(entry -> entry[0], entry-> entry[1]));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException : " + e.getMessage());
        }
    }
}

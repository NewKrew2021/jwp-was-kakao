package webserver.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FormUrlencodedBodyParser {

    public static Map<String, String> parse(String raw) throws UnsupportedEncodingException {
        Map<String, String> body = new HashMap<>();
        String urlDecoded = URLDecoder.decode(raw, "utf-8");
        String[] tokens = urlDecoded.split("&");
        Stream.of(tokens)
                .map(param -> param.split("="))
                .forEach(p -> body.put(p[0], p[1]));

        return body;
    }
}

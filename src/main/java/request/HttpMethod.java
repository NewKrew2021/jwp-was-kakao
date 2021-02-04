package request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpMethod {

    GET, POST;

    private static final Map<String, HttpMethod> mappings = Arrays.stream(values())
            .collect(Collectors.toMap(HttpMethod::name, Function.identity()));
    
    public static HttpMethod of(String method) {
        return mappings.get(method.toUpperCase());
    }

}

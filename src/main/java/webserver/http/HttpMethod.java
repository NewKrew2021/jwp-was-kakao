package webserver.http;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod getHttpMethod(String methodName){
        return Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, method -> method))
        .get(methodName);
    }
}

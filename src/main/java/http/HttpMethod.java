package http;

import exception.MethodMappingException;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String method) {
        if (GET.name().equals(method)) {
            return GET;
        }
        if (POST.name().equals(method)) {
            return POST;
        }
        throw new MethodMappingException(String.format("지원되지 않는 메서드입니다: %s", method));
    }
}

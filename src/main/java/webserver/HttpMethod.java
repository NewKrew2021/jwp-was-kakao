package webserver;

import annotation.exception.NotSupportedMethodException;

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
        throw new NotSupportedMethodException(method);
    }
}

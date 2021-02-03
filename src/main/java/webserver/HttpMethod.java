package webserver;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod from(String method) {
        if ("GET".equals(method)) {
            return GET;
        }
        if ("POST".equals(method)) {
            return POST;
        }
        throw new IllegalArgumentException(String.format("잘못된 HTTP 메서드 문자열입니다. - %s", method));
    }
}

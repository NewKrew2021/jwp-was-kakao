package webserver;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod of(String name) {
        return HttpMethod.valueOf(name.toUpperCase());
    }
}

package annotation.web;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod of(String methodValue) {
        return RequestMethod.valueOf(methodValue.toUpperCase());
    }
}

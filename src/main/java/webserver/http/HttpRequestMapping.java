package webserver.http;

public interface HttpRequestMapping<T> {

    boolean matches(HttpRequest httpRequest);

    T getTarget();

}

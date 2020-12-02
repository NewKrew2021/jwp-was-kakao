package webserver.http;

public interface HttpRequestMapper<T> {

    void addMapping(HttpRequestMapping<T> mapping);

    void addMapping(HttpRequestMapping<T>... mappings);

    T getTarget(HttpRequest httpRequest);

}

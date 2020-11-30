package webserver.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UriBaseHttpRequestMapper<T> implements HttpRequestMapper {

    private List<HttpRequestMapping<T>> mappings = new ArrayList<>();

    public UriBaseHttpRequestMapper(List<HttpRequestMapping<T>> mappings) {
        this.mappings.addAll(mappings);
    }

    public UriBaseHttpRequestMapper(HttpRequestMapping<T>... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    @Override
    public void addMapping(HttpRequestMapping mapping){
        mappings.add(mapping);
    }

    @Override
    public void addMapping(HttpRequestMapping... mappings) {
        Arrays.stream(mappings).forEach(this::addMapping);
    }

    @Override
    public T getTarget(HttpRequest httpRequest) {
        return mappings.stream()
                .filter(it -> it.matches(httpRequest))
                .map(HttpRequestMapping::getTarget)
                .findFirst()
                .orElse(null);

    }

}


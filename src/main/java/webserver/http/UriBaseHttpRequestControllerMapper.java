package webserver.http;

import webserver.http.controller.Controllers;
import webserver.http.dispatcher.HttpRequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UriBaseHttpRequestControllerMapper implements HttpRequestControllerMapper {

    private List<HttpRequestMapping> mappings = new ArrayList<>();

    public UriBaseHttpRequestControllerMapper(List<HttpRequestMapping> mappings) {
        this.mappings.addAll(mappings);
    }

    public UriBaseHttpRequestControllerMapper(HttpRequestMapping... mappings) {
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
    public Controller getController(HttpRequest httpRequest) {
        return mappings.stream()
                .filter(it -> it.matches(httpRequest))
                .map(HttpRequestMapping::getController)
                .findFirst()
                .orElse(Controllers.NOT_FOUND);
    }

}


package webserver.http;

import webserver.http.controller.Controller;
import webserver.http.controller.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UriBaseHttpRequestControllerMapper implements HttpRequestControllerMapper {

    private List<RegexpMapping> mappings = new ArrayList<>();

    public static UriBaseHttpRequestControllerMapper withDefaultMappings() {
        return new UriBaseHttpRequestControllerMapper(
                new RegexpMapping("\\/css\\/.+", HttpMethod.GET, Controllers.STATIC_RESOURCE),
                new RegexpMapping("\\/js\\/.+", HttpMethod.GET, Controllers.STATIC_RESOURCE),
                new RegexpMapping("\\/fonts\\/.+", HttpMethod.GET, Controllers.STATIC_RESOURCE)
        );
    }

    public UriBaseHttpRequestControllerMapper(RegexpMapping... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    @Override
    public void addMapping(RegexpMapping mapping){
        mappings.add(mapping);
    }

    @Override
    public void addMapping(RegexpMapping... mappings) {
        Arrays.stream(mappings).forEach(this::addMapping);
    }

    @Override
    public Controller getController(HttpRequest httpRequest) {
        return mappings.stream()
                .filter(it -> it.matches(httpRequest))
                .map(RegexpMapping::getController)
                .findFirst()
                .orElse(Controllers.NOT_FOUND);
    }

}


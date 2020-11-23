package webserver.http;

import webserver.http.controller.Controller;
import webserver.http.controller.Controllers;
import webserver.http.controller.TemplateController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UriBaseHttpRequestControllerMapper implements HttpRequestControllerMapper {

    private List<RegexpUriMapping> mappings = new ArrayList<>();

    public static UriBaseHttpRequestControllerMapper withDefaultMappings() {
        return new UriBaseHttpRequestControllerMapper(
                new RegexpUriMapping("\\/css\\/.+", Controllers.STATIC_RESOURCE),
                new RegexpUriMapping("\\/js\\/.+", Controllers.STATIC_RESOURCE),
                new RegexpUriMapping("\\/fonts\\/.+", Controllers.STATIC_RESOURCE),
                new RegexpUriMapping("\\/.+\\.html", new TemplateController())
        );
    }

    public UriBaseHttpRequestControllerMapper(RegexpUriMapping... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    @Override
    public void addMapping(RegexpUriMapping mapping){
        mappings.add(mapping);
    }

    public void addMapping(String regexp, Controller controller) {
        addMapping(new RegexpUriMapping(regexp, controller));
    }

    @Override
    public Controller getController(HttpRequest httpRequest) {
        return mappings.stream()
                .filter(it -> it.matches(httpRequest.getRequestUri()))
                .map(RegexpUriMapping::getController)
                .findFirst()
                .orElse(Controllers.NOT_FOUND);
    }

}


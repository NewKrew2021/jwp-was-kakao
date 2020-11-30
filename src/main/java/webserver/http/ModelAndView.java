package webserver.http;

import java.util.Map;

public class ModelAndView {

    private String view;
    private Map<String, String> model;

    public ModelAndView(String view, Map<String, String> model) {
        this.view = view;
        this.model = model;
    }

}
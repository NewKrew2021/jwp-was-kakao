package webserver.http;

import com.google.common.collect.Maps;

import java.util.Map;

public class ModelAndView {

    private View view;
    private Map<String, Object> model;

    private ModelAndView(Map<String, Object> model, View view) {
        this.model = model;
        this.view = view;
    }

    public static ModelAndView of(View view) {
        return new ModelAndView(Maps.newHashMap(), view);
    }

    public static ModelAndView of(Map<String, Object> model, View view) {
        return new ModelAndView(model, view);
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View getView() {
        return view;
    }
}

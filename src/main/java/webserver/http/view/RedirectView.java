package webserver.http.view;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.View;

import java.util.Map;

public class RedirectView implements View {

    private String location;

    public RedirectView(String location) {
        this.location = location;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendRedirect(location);
    }
}

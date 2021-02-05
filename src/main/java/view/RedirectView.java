package view;

import webserver.Model;
import webserver.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RedirectView implements View {
    private String location;

    @Override
    public boolean canHandle(String name) {
        if (!name.startsWith("redirect:")) {
            return false;
        }
        String parsed[] = name.split(":", 2);
        location = parsed.length == 2 ? parsed[1] : null;
        return true;
    }

    @Override
    public void render(Model model, HttpRequest request, HttpResponse httpResponse) {
        httpResponse.setStatus("HTTP/1.1 302 FOUND");
        httpResponse.addHeader("Location", location);
    }
}

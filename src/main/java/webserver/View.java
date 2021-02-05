package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface View {
    boolean canHandle(String name);

    void render(Model model, HttpRequest request, HttpResponse httpResponse);
}

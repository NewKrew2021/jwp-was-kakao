package controller;

import http.HttpRequest;
import http.HttpResponse;
import model.MimeType;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private static final Map<MimeType, String> contentLocation = new HashMap<>();

    static {
        contentLocation.put(MimeType.getMimeType(".ico"), "./templates");
        contentLocation.put(MimeType.getMimeType(".html"), "./templates");
    }

    protected static String getContentLocation(String content) {
        return contentLocation.getOrDefault(MimeType.getMimeType(content), "./static") + content;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    public abstract void doPost(HttpRequest request, HttpResponse response) throws Exception;

    public abstract void doGet(HttpRequest request, HttpResponse response) throws Exception;
}

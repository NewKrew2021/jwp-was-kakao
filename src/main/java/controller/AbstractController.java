package controller;

import org.springframework.http.HttpMethod;
import utils.ParseUtils;
import webserver.Request;
import webserver.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractController implements Controller {
    private static Map<String, String> contentLocation = new HashMap<>();

    static {
        contentLocation.put("ico", "./templates/");
        contentLocation.put("html", "./templates/");
    }

    protected static String getContentLocation(String content) {
        return contentLocation.getOrDefault(ParseUtils.parseExtension(content), "./static") + content;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    public abstract void doPost(Request request, Response response)  throws Exception;

    public abstract void doGet(Request request, Response response) throws Exception;
}

package application;

import domain.HttpRequest;
import domain.HttpResponse;

public class MainController extends AbstractController{
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String url = httpRequest.getUrl();
        url = setPrefixUrl(url);
        httpResponse.forward(url);
    }

    private String setPrefixUrl(String url) {
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".ico") || url.endsWith(".ttf") || url.endsWith(".woff")) {
            return "./static" + url;
        }
        return "./templates" + url;
    }

}

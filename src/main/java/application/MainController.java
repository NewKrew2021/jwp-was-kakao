package application;

import domain.HttpRequest;
import domain.HttpResponse;

public class MainController extends AbstractController{

    public static final String CSS = ".css";
    public static final String JS = ".js";
    public static final String WOFF = ".woff";
    public static final String TTF = ".ttf";
    public static final String ICO = ".ico";
    public static final String STATIC = "./static";
    public static final String TEMPLATES = "./templates";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String url = httpRequest.getUrl();
        url = setPrefixUrl(url);
        httpResponse.forward(url);
    }

    private String setPrefixUrl(String url) {
        if (url.endsWith(CSS) || url.endsWith(JS) || url.endsWith(ICO) || url.endsWith(TTF) || url.endsWith(WOFF)) {
            return STATIC + url;
        }
        return TEMPLATES + url;
    }

}

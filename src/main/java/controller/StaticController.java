package controller;

import com.github.jknack.handlebars.io.TemplateLoader;
import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    private static final String TEMPLATES_FILE_PATH = "src/main/resources/templates";
    private static final String STATIC_FILE_PATH = "src/main/resources/static";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body = null;
        try {
            File file = new File(TEMPLATES_FILE_PATH + httpRequest.getUri());
            if (file.exists()) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getUri());
                httpResponse.response200Header(body.length);
                httpResponse.responseBody(body);
                return;
            }

            file = new File(STATIC_FILE_PATH + httpRequest.getUri());
            if (file.exists()) {
                body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getUri());
                httpResponse.response200HeaderForCss(body.length);
                httpResponse.responseBody(body);
                return;
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}

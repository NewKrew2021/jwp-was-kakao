package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import user.controller.UserController;
import utils.FileIoUtils;
import webserver.model.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController implements Controller {


    private static final String path = "/user/list";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        if (isLogined(httpRequest)) {
            return loginFailedService(httpResponse);
        }

        return loginSuccessedService(httpResponse);
    }

    private boolean isLogined(HttpRequest httpRequest) {
        return httpRequest.getCookie(Cookie.LOGINED) == null
                || httpRequest.getCookie(Cookie.LOGINED).equals(Cookie.LOGINED_FALSE);
    }

    private HttpResponse loginSuccessedService(HttpResponse httpResponse) {
        String body = null;
        try {
            body = makeUserListHTML();
        } catch (IOException e) {
        }

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, findContentType(path) + "; charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.addHeader(HttpHeader.CONTENT_LOCATION, path);
        httpResponse.setBody(body);

        return httpResponse;
    }

    private HttpResponse loginFailedService(HttpResponse httpResponse) {
        String body = null;
        try {
            body = FileIoUtils.loadFileStringFromClasspath(FileIoUtils.TEMPLATES_PATH + FileIoUtils.LOGIN_PATH);
        } catch (IOException e) {
        } catch (URISyntaxException e) {
        }

        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, "text/html; charset=utf-8");
        httpResponse.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.addHeader(HttpHeader.CONTENT_LOCATION, FileIoUtils.LOGIN_PATH);
        httpResponse.setBody(body);

        return httpResponse;
    }

    private String makeUserListHTML() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(path);
        String profilePage = template.apply(UserController.findAll());

        return profilePage;
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }

        return "text/html";
    }

}

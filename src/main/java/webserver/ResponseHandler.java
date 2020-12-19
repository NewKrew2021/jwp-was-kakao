package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HandlebarTemplateLoader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private static final String TEMPLATE_DIR = "./templates";
    private static final String ASSET_DIR = "./static";

    public static void handle(HttpRequest request, HttpResponse response) throws IOException {

        String contentType = request.getContentType();
        String requestPath = request.getPath();
        byte[] bodyContent = new byte[0];

        if (requestPath.equals("/") && request.isGetMethod()) {
            response.response302Header("/index.html");
            return ;
        }

        if (requestPath.equals("/user/create") && request.isPostMethod()) {

            User user = request.user();

            DataBase.addUser(user);

            response.response302Header("/index.html");
            return;
        }

        if (requestPath.equals("/user/login") && request.isPostMethod()) {

            String userId = request.getParam("userId");
            String password = request.getParam("password");

            User foundUser = DataBase.findUserById(userId);

            if(foundUser == null || !foundUser.getPassword().equals(password)) {
                response.addHeaderValue("Set-Cookie", "logined=false; Path=/");
                response.response302Header("/user/login_failed.html");
            }else{
                response.addHeaderValue("Set-Cookie", "logined=true; Path=/");
                response.response302Header("/index.html");
            }
            return ;
        }

        if (requestPath.equals("/user/list") && request.isGetMethod()) {

            String logined = request.getCookie("logined");
            if (logined != null && logined.equals("true")) {

                Collection<User> users = DataBase.findAll();
                for(User user: users) {
                    logger.debug(user.toString());
                }
                HandlebarTemplateLoader loader = new HandlebarTemplateLoader("user/list");

                String pageContent = loader.applyTemplateValue(Collections.singletonMap("users", users));

                byte[] body = pageContent.getBytes();
                response.response200Header(pageContent.length(), contentType);
                response.responseBody(body);
            }else{
                response.response302Header("/index.html");
            }
            return ;
        }


        String fileLocation = getPath(request.getPath());

        try {
            bodyContent = FileIoUtils.loadFileFromClasspath(fileLocation);
        } catch (IOException | URISyntaxException e) {
            response.response404Header();
            response.responseBody(bodyContent);
            return;
        }

        response.response200Header(bodyContent.length, contentType);
        response.responseBody(bodyContent);

    }

    private static String getPath(String uri) {

        return Arrays.asList("/css", "/fonts", "/images", "/js")
                .stream()
                .filter(uri::startsWith)
                .findAny()
                .map(t -> ASSET_DIR.concat(uri))
                .orElse(TEMPLATE_DIR.concat(uri));
    }

}

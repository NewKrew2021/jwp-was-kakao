package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private static final String TEMPLATE_DIR = "./templates";
    private static final String ASSET_DIR = "./static";

    public static void handle(HttpRequest request, HttpResponse response) {

        String contentType = request.getContentType();

        String requestPath = request.getPath();
        byte[] bodyContent = new byte[0];

        if (requestPath.equals("/user/create") && request.isMethod(HttpMethod.POST)) {

            User user = request.user();

            DataBase.addUser(user);

            response.response302Header("/index.html");
            return;
        }

        if (requestPath.equals("/user/login") && request.isMethod(HttpMethod.POST)) {


            String userId = request.getParam("userId");
            String password = request.getParam("password");

            User foundUser = DataBase.findUserById(userId);

            if(foundUser == null || !foundUser.getPassword().equals(password)) {
                response.response302Header("/user/login_failed.html");
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

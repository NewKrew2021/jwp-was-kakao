package webserver;

import controller.*;
import http.HttpRequest;
import http.HttpResponse;
import model.PagePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private Map<PagePath, Controller> controllers;

    public RequestHandler(Socket connectionSocket) {
        connection = connectionSocket;
        controllers = new HashMap<>();
        AllocateUrlAtControllers();
    }

    private void AllocateUrlAtControllers() {
        controllers.put(new PagePath("/user/create"), new CreateUserController() );
        controllers.put(new PagePath("/user/login") , new LoginController());
        controllers.put(new PagePath("/user/list.html") , new ListUserController());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out, httpRequest.getHttpHeader());
            PagePath pagePath = httpRequest.getPath();

            if( controllers.containsKey(pagePath) ) {
                controllers.get(pagePath).service(httpRequest,httpResponse);
            }

            new FowardController().service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

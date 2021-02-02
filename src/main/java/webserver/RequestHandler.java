package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static Map<String, Controller> pathMap = new HashMap<>();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = new Request(in);
            Response response = new Response(out);
            requestMapper(request, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void requestMapper(Request request,Response response) throws IOException, URISyntaxException {
        Map<String,Controller> pathMap=new HashMap<>();
        pathMap.put("/user/create", new CreateUserController());
        pathMap.put("/user/list", new ListUserController());
        pathMap.put("/user/login", new LoginController());
        if(pathMap.get(request.getPath()) == null) {
            response.forward(request.getPath());
            return;
        }
        pathMap.get(request.getPath()).service(request,response);
    }

}

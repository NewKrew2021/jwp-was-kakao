package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

import controller.Controller;
import controller.ControllerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(){}

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            httpResponse.addHeader("Content-Type", getContentType(httpRequest));


            String path = parsePath(httpRequest.getPath());
            Controller controller = ControllerEntity.getControllers(path);
            controller.service(httpRequest, httpResponse);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    
    private String getContentType(HttpRequest httpRequest) {
        return httpRequest.getHeader("Accept").split(",")[0] + ";charset=utf-8";
    }

    private String parsePath(String path) {
        String prefix = path.split("/")[1];

        if (prefix.equals("js") || prefix.equals("css") || prefix.equals("fonts")){
            return "/" + prefix;
        }
        return path;
    }
    
}

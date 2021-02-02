package webserver;

import controller.UserController;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            Request request = new Request(in);

//            if(str.contains(".css")){
//                Response response = new Response();
//                response.setCssResponse200Header();
//                response.setPath(request.getPath());
//                return;
//            }
//            if(str.contains(".js")){
//                Response response = new Response();
//                response.setJsResponse200Header();
//                response.setPath(request.getPath());
//                return;
//            }

            Response response = requestMapper(request);
            dosWriteResponse(dos, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Response requestMapper(Request request) throws IOException, URISyntaxException {
        String[] paths = request.getPath().split("/");
        if(paths.length>=1 && paths[1].equals("user")){
            return new UserController().mapMethod(request);
        }
        Response response = new Response();
        response.setPath(request.getPath());
        response.setResponse200Header();
        return response;
    }



    private void dosWriteResponse(DataOutputStream dos, Response response) {
        try {
            dos.write(response.getHeader().getBytes());
            dos.write(response.getCookie().getBytes());
            dos.write(response.getBody());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

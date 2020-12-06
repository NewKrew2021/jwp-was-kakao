package webserver;

import domain.HttpRequest;
import domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequstParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest request = new HttpRequstParser(bufferedReader).getHttpRequest();
            HttpResponse response = new HttpResponse(out);
            new ControllerRouter(request, response).route();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

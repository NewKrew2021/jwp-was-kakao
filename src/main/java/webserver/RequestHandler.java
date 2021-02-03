package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.LoginController;
import controller.UserController;
import domain.Dispatcher;
import domain.Request;
import domain.Response;
import domain.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Dispatcher dispatcher = Dispatcher.getInstance();
    private final UserController userController = UserController.getInstance();
    private final LoginController loginController = LoginController.getInstance();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        userController.registerAll();
        loginController.registerAll();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            List<String> lines = Arrays.stream(IOUtils.readData(br, 5000).split("\r\n"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            Request request = new Request(lines);
            System.out.println(request);

            Response response = Response.ofDefaultFile(new ResponseBody("No Page"));

            if (FileIoUtils.pathIsFile(request.getUrlPath())) {
                response = FileIoUtils.loadFileFromUrlPath(request.getUrlPath());
            } else {
                response = dispatcher.run(request);
            }

            printResponse(out, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printResponse(OutputStream out, Response response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(response.toBytes(),
                0,
                response.toBytes().length);
        dos.flush();
    }
}

package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(InputStream in, OutputStream out) throws IOException {
        InputView inputView = InputView.from(in);
        OutputView outputView = OutputView.from(out);
        try {
            HttpRequest request = inputView.getHttpRequest();
            request.logRequestMessage();

            ControllerMapper mapper = ControllerMapper.getInstance();
            Controller controller = mapper.getController(request.getPath());

            HttpResponse response = controller.service(request);
            response.logResponseHeader();
            outputView.write(response);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            HttpResponse response = ExceptionHandler.handle(exception);
            response.logResponseHeader();
            outputView.write(response);
        }
    }
}

package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.request.RequestReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static context.ApplicationContext.dispatcher;
import static context.ApplicationContext.exceptionHandler;

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
            dispathWithException(in, out);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void dispathWithException(InputStream in, OutputStream out) {
        try {
            dispatcher.dispatch(readRequest(in), new DataOutputStream(out));
        } catch (Exception ex) {
            exceptionHandler.handleException(out);
        }
    }

    private HttpRequest readRequest(InputStream in) {
        return RequestReader.read(new BufferedReader(new InputStreamReader(in)));
    }
}

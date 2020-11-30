package webserver;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.TemplateUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = parseRequest(in);
            String requestURI = httpRequest.getRequestURI();
            logger.debug("requestURI: {}", requestURI);

            Response response = handleRequest(httpRequest);

            response(new DataOutputStream(out), response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Response handleRequest(HttpRequest httpRequest) throws Exception {
        return Optional.ofNullable(getController(httpRequest))
                .orElseGet(StaticContentController::new)
                .execute(httpRequest);
    }

    private Controller getController(HttpRequest httpRequest) {
        return new RequestMapping(ImmutableMap.of(
                "/usr/create", httpRequest1 -> new CreateUserController().execute(httpRequest1),
                "/user/login", httpRequest2 -> new LoginController().execute(httpRequest2),
                "/user/list", httpRequest3 -> new UserListController().execute(httpRequest3)))
                .getController(httpRequest.getRequestURI());
    }

    private void response(DataOutputStream dos, Response response) throws IOException {
        if (response.isRedirect()) {
            response302Header(dos, response.getHeaders());
        }

        byte[] body = getContentBody(response);
        response.setHeaders("Content-Length: " + body.length);

        response200Header(dos, response.getHeaders());
        responseBody(dos, body);
    }

    private HttpRequest parseRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestParser requestParser = new RequestParser(bufferedReader);
        return requestParser.parse();
    }

    private byte[] getContentBody(Response response) throws IOException {
        if (StringUtils.hasText(response.getViewName())) {
            return TemplateUtils.getTemplate(response.getViewName())
                    .apply(response.getModel())
                    .getBytes(UTF_8);
        }

        if (response.getBody() != null) {
            return response.getBody();
        }
        return new byte[0];
    }

    private void response200Header(DataOutputStream dos, List<String> headers) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            writeHeaders(dos, headers);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, List<String> headers) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            writeHeaders(dos, headers);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(DataOutputStream dos, List<String> headers) throws IOException {
        for (String header : headers) {
            dos.writeBytes(header + "\r\n");
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

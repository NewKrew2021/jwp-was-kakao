package webserver;

import com.github.jknack.handlebars.Template;
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
    private final RequestMapping requestMapping;

    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
        this.connection = connectionSocket;
        this.requestMapping = requestMapping;
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
        return Optional.ofNullable(requestMapping.getController(httpRequest.getRequestURI()))
                .orElseGet(StaticContentController::new)
                .execute(httpRequest);
    }

    void response(DataOutputStream dos, Response response) throws IOException {
        if (StringUtils.hasText(response.getViewName())) {
            setBody(response);
        }

        responseHeader(dos, response.getHeaders());
        responseBody(dos, response.getBody());
    }

    private HttpRequest parseRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestParser requestParser = new RequestParser(bufferedReader);
        return requestParser.parse();
    }

    private void setBody(Response response) throws IOException {
        response.setBody(getTemplate(response).apply(response.getModel())
                .getBytes(UTF_8));
    }

    Template getTemplate(Response response) throws IOException {
        return TemplateUtils.getTemplate(response.getViewName());
    }

    private void responseHeader(DataOutputStream dos, List<String> headers) throws IOException {
        for (String header : headers) {
            dos.writeBytes(header + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        if (body == null) {
            return;
        }
        dos.write(body, 0, body.length);
        dos.flush();
    }

}

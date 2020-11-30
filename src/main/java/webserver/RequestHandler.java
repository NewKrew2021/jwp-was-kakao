package webserver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;
import utils.TemplateUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

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

            if (response != null) {
                response(new DataOutputStream(out), response);
                return;
            }

            responseStaticContent(requestURI, new DataOutputStream(out));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Response handleRequest(HttpRequest httpRequest) throws Exception {
        Controller controller = new RequestMapping(ImmutableMap.of(
                "/usr/create", httpRequest1 -> new CreateUserController().execute(httpRequest1),
                "/user/login", httpRequest2 -> new LoginController().execute(httpRequest2),
                "/user/list", httpRequest3 -> new UserListController().execute(httpRequest3)))
                .getController(httpRequest.getRequestURI());
        if (controller == null) {
            return null; // TODO 향후 static controller 로 교체한다
        }
        return controller.execute(httpRequest);
    }

    private void response(DataOutputStream dos, Response response) throws IOException {
        if (response.isRedirect()) {
            response302Header(dos, response.getHeaders());
        }

        if (StringUtils.hasText(response.getViewName())) {
            byte[] body = TemplateUtils.getTemplate(response.getViewName())
                    .apply(response.getModel())
                    .getBytes(UTF_8);

            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }

    private void responseStaticContent(String requestURI, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getBasePath(requestURI) + requestURI);
        response200Header(dos, body.length, getContentType(requestURI));
        responseBody(dos, body);
    }

    public static String getContentType(String requestURI) {
        switch (requestURI.substring(requestURI.lastIndexOf(".") + 1)) {
            case "js":
                return "application/js";
            case "css":
                return "text/css";
            default:
                return "text/html;charset=utf-8";
        }
    }

    public static String getBasePath(String requestURI) {
        return Sets.newHashSet("/css", "/js", "/fonts", "/images")
                .stream()
                .filter(requestURI::startsWith)
                .findAny()
                .map(path -> STATIC_PATH)
                .orElse(TEMPLATE_PATH);
    }

    private HttpRequest parseRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestParser requestParser = new RequestParser(bufferedReader);
        return requestParser.parse();
    }

    private void response200Header(DataOutputStream dos, int length) {
        response200Header(dos, length, "text/html;charset=utf-8");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void response302Header(DataOutputStream dos, List<String> headers) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            for (String header : headers) {
                dos.writeBytes(header + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
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

package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

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
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Response handleRequest(HttpRequest httpRequest) {
        Controller controller = new RequestMapping(ImmutableMap.of(
                "/usr/create", RequestHandler::handleUserCreate,
                "/user/login", RequestHandler::handleLogin,
                "/user/list", RequestHandler::handleList))
                .getController(httpRequest.getRequestURI());
        if (controller == null) {
            return null; // TODO 향후 static controller 로 교체한다
        }
        return controller.execute(httpRequest);
    }

    private void response(DataOutputStream dos, Response response) throws IOException {
        if (StringUtils.hasText(response.getLocation())) {
            response302Header(dos, response.getLocation(), response.getHeaders());
        }

        if (StringUtils.hasText(response.getViewName())) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);

            Template template = handlebars.compile(response.getViewName());
            byte[] body = template.apply(response.getModel()).getBytes(UTF_8);
            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }

    public static Response handleList(HttpRequest httpRequest) {
        if (!httpRequest.getCookies().contains("logined=true")) {
            Response response = new Response();
            response.setLocation("/user/login.html");
            response.setHeaders("Location: /user/login.html");
            return response;
        }

        Response response = new Response();
        response.setModel(DataBase.findAll());
        response.setViewName("user/list");
        return response;
    }

    public static  Response handleLogin(HttpRequest httpRequest) {
        Map<String, String> entity = httpRequest.getEntity();
        User user = DataBase.findUserById(entity.get("userId"));
        if (user.getPassword().equals(entity.get("password"))) {
            Response response = new Response();
            response.setLocation("/index.html"); // TODO 로케이션 헤더로 변경
            response.setHeaders("Set-Cookie: logined=true; Path=/");
            response.setHeaders("Location: /index.html");
            return response;
        }

        Response response = new Response();
        response.setLocation("/user/login_failed.html");
        response.setHeaders("Set-Cookie: logined=false; Path=/");
        response.setHeaders("Location: /user/login_failed.html");
        return response;
    }

    public static  Response handleUserCreate(HttpRequest httpRequest) {
        User user = User.createUser(httpRequest.getEntity());
        DataBase.addUser(user);
        Response response = new Response();
        response.setLocation("/index.html");
        return response;
    }

    private void responseStaticContent(String requestURI, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getBasePath(requestURI) + requestURI);
        response200Header(dos, body.length, getContentType(requestURI));
        responseBody(dos, body);
    }

    private String getContentType(String requestURI) {
        switch (requestURI.substring(requestURI.lastIndexOf(".") + 1)) {
            case "js":
                return "application/js";
            case "css":
                return "text/css";
            default:
                return "text/html;charset=utf-8";
        }
    }

    private String getBasePath(String requestURI) {
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


    private void response302Header(DataOutputStream dos, String location, List<String> headers) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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

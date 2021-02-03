package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpMethod;
import request.HttpRequest;
import response.Response200Header;
import response.Response302Header;
import response.Response404Header;
import response.ResponseHeader;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.ParseUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String HTTP_GET = "GET";
    private final String HTTP_POST = "POST";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public synchronized void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = readLines(reader);
            Map<String, String> responseParameters = new HashMap<>();

            if (httpRequest.getMethod().equals(HttpMethod.GET)) {
                if (httpRequest.getPath().equals("/user/list")) {
                    String cookie = httpRequest.getHeaders().get("Cookie");
                    if (cookie == null || !cookie.contains("logined=true")) {
                        responseParameters.put("Location", "/user/login.html");
                        responseParameters.put("body", "");
                        response(dos, new Response302Header(), responseParameters);
                        return;
                    }

                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);

                    Template template = handlebars.compile("user/list");

                    Map<String, List> parameters = new HashMap<>();
                    List<User> users = new ArrayList<>(DataBase.findAll());
                    parameters.put("users", users);

                    String profilePage = template.apply(parameters);

                    responseParameters.put("body", profilePage);
                    response(dos, new Response302Header(), responseParameters);
                    return;
                }
            }

            if (httpRequest.getMethod().equals(HttpMethod.POST)) {

                if (httpRequest.getPath().equals("/user/create")) {
                    User user = User.mapOf(httpRequest.getBodys());
                    DataBase.addUser(user);
                    responseParameters.put("Location", "/index.html");
                    responseParameters.put("body", "");
                    response(dos, new Response302Header(), responseParameters);
                    return;
                }

                if (httpRequest.getPath().equals("/user/login")) {
                    Map<String, String> parameters = httpRequest.getBodys();
                    User user = DataBase.findUserById(parameters.get("userId"));
                    if (user == null || !user.getPassword().equals(parameters.get("password"))) {
                        responseParameters.put("Location", "/user/login_failed.html");
                        responseParameters.put("Set-Cookie", "logined=false");
                    } else {
                        responseParameters.put("Location", "/index.html");
                        responseParameters.put("Set-Cookie", "logined=true; Path=/");
                    }
                    responseParameters.put("body", "");
                    response(dos, new Response302Header(), responseParameters);
                    return;
                }
            }

            responseParameters.put("path", httpRequest.getPath());
            responseParameters.put("body", "");

            response(dos, new Response200Header(), responseParameters);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest readLines(BufferedReader reader) throws IOException{
        List<String> lines = new ArrayList<>();
        lines.add(readLine(reader));

        while(lines.get(lines.size() - 1) != null &&
                !lines.get(lines.size() - 1).isEmpty()){
            lines.add(readLine(reader));
        }

        HttpRequest httpRequest = HttpRequest.of(lines);

        if(httpRequest.getMethod().equals(HttpMethod.POST)){
            String body = IOUtils.readData(reader, Integer.parseInt(httpRequest.getHeaders().get("Content-Length")));
            httpRequest.parsePostMethodBody(body);
        }

        if(httpRequest.getMethod().equals(HttpMethod.GET)){
            httpRequest.parseGetMethodBody();
        }

        return httpRequest;
    }

    private void response(DataOutputStream dos, ResponseHeader responseHeader, Map<String, String> responseParameters)
            throws IOException, URISyntaxException {
        try {
            byte[] responseBody = responseParameters.get("body").getBytes();
            responseParameters.remove("body");

            if (responseParameters.containsKey("path")) {
                String path = responseParameters.get("path");
                if(path.endsWith(".css")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","text/css");
                }
                else if (path.endsWith(".js")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","application/js");
                }
                else if (path.endsWith(".png")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","image/png");
                }
                else if (path.endsWith(".eot")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","application/vnd.ms-fontobject");
                }
                else if (path.endsWith(".svg")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","image/svg+xml");
                }
                else if (path.endsWith(".ttf")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","application/octet-stream");
                }
                else if (path.endsWith(".woff") || path.endsWith(".woff2")){
                    responseBody = FileIoUtils.loadFileFromClasspath("./static" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","application/font-woff");
                }
                else{
                    responseBody = FileIoUtils.loadFileFromClasspath("./templates" + responseParameters.get("path"));
                    responseParameters.put("Content-Type","text/html");
                }
            }
            responseParameters.remove("path");

            responseParameters.put("Content-Length", String.valueOf(responseBody.length));
            responseHeader.responseHeader(dos, responseParameters);
            responseBody(dos, responseBody);
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            new Response404Header().responseHeader(dos, responseParameters);
        }
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        logger.debug(line);
        return line;
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

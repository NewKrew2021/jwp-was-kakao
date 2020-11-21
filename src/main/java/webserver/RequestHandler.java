package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.UsersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.RequestParser;
import webserver.request.ContentType;
import webserver.request.Protocol;
import webserver.request.Request;
import webserver.request.RequestHeader;
import webserver.request.Status;
import webserver.response.Response;
import webserver.response.ResponseHeader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_BODY = "Hello World";
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String USER_LIST_PATH = "/user/list";

    private Socket connection;
    private UserService userService;
    private Handlebars handlebars;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        userService = new UserService();
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            RequestHeader requestHeader = RequestHeader.of(readRequestHeader(bufferedReader));
            print(requestHeader);
            Request request = new Request(requestHeader, getRequestBody(bufferedReader, requestHeader));
            renderResponse(new DataOutputStream(out), getResponse(request));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Response getResponse(Request request) {
        String path = request.getHeader().getPath();
        if (USER_CREATE_PATH.equals(path)) {
            String method = request.getHeader().getMethod();
            if ("GET".equals(method)) {
                userService.addUser(request.getHeader().getParams());
            }
            if ("POST".equals(method)) {
                userService.addUser(RequestParser.getRequestParamsFromBody(request.getBody()));
            }
            return new Response(
                    ResponseHeader.builder()
                            .protocol(Protocol.HTTP)
                            .status(Status.REDIRECT)
                            .location("http://" + request.getHeader().getHost() + "/index.html")
                            .build()
            );
        }
        if (USER_LOGIN_PATH.equals(path)) {
            String method = request.getHeader().getMethod();
            if ("POST".equals(method)) {
                if (userService.login(RequestParser.getRequestParamsFromBody(request.getBody()))) {
                    return new Response(
                            ResponseHeader.builder()
                                    .protocol(Protocol.HTTP)
                                    .status(Status.REDIRECT)
                                    .cookie("logined=true", "/")
                                    .location("http://" + request.getHeader().getHost() + "/index.html")
                                    .build()
                    );
                }
            }
            return new Response(
                    ResponseHeader.builder()
                            .protocol(Protocol.HTTP)
                            .status(Status.REDIRECT)
                            .cookie("logined=false", "/")
                            .location("http://" + request.getHeader().getHost() + "/user/login_failed.html")
                            .build()
            );
        }
        if (USER_LIST_PATH.equals(path)) {
            if (request.getHeader().isLogined()) {
                byte[] usersBody;
                try {
                    usersBody = handlebars.compile("user/list").apply(new UsersDto(userService.findAll())).getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    usersBody = "".getBytes();
                }
                return new Response(
                        ResponseHeader.builder()
                                .protocol(Protocol.HTTP)
                                .status(Status.OK)
                                .contentType(ContentType.HTML)
                                .contentLength(usersBody.length)
                                .build(),
                        usersBody
                );
            }
            return new Response(
                    ResponseHeader.builder()
                            .protocol(Protocol.HTTP)
                            .status(Status.REDIRECT)
                            .location("http://" + request.getHeader().getHost() + "/user/login.html")
                            .build());
        }
        byte[] bodyFromFile;
        try {
            bodyFromFile = FileIoUtils.loadFileFromClasspath(request.getHeader().getPath());
        } catch (Exception e) {
            e.printStackTrace();
            bodyFromFile = DEFAULT_BODY.getBytes();
        }
        if (path.endsWith(".css")) {
            return new Response(
                    ResponseHeader.builder()
                            .protocol(Protocol.HTTP)
                            .status(Status.OK)
                            .contentType(ContentType.CSS)
                            .contentLength(bodyFromFile.length)
                            .build(),
                    bodyFromFile
            );
        }
        return new Response(
                ResponseHeader.builder()
                        .protocol(Protocol.HTTP)
                        .status(Status.OK)
                        .contentType(ContentType.HTML)
                        .contentLength(bodyFromFile.length)
                        .build(),
                bodyFromFile
        );
    }

    private String getRequestBody(BufferedReader bufferedReader, RequestHeader requestHeader) {
        return Optional.ofNullable(requestHeader.getContentLength())
                .map(contentLength -> readRequestBody(bufferedReader, contentLength))
                .orElse("");
    }

    private String readRequestBody(BufferedReader bufferedReader, Integer contentLength) {
        try {
            return IOUtils.readData(bufferedReader, contentLength);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void print(RequestHeader requestHeader) {
        System.out.println(requestHeader);
        System.out.println();
    }

    private List<String> readRequestHeader(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }

    private void renderResponse(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes(response.getHeader());
            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

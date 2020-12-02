package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.HttpHeader;
import domain.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MemberService;
import utils.FileIoUtils;
import utils.HttpRequstParser;
import utils.RequestPathUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_RESPONSE = "Hello World";
    private static final MemberService memberService = new MemberService();
    private static final Handlebars handlebars;
    private Socket connection;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequstParser requstParser = new HttpRequstParser(bufferedReader);
            HttpRequest httpRequest = requstParser.requestParse();
            printRequestHeaders(httpRequest.getHeaders()); //헤더 출력

            DataOutputStream dos = new DataOutputStream(out);
            if (memberService.isMemberJoinRequst(httpRequest)) {
                memberService.joinMember(httpRequest.getRequestParam());
                response302Header(dos, "/index.html", "");
                return;
            }
            if (memberService.memberLoginRequest(httpRequest)) {
                boolean isLogin = memberService.memberLogin(httpRequest.getRequestParam());
                if (!isLogin) {
                    response302Header(dos, "/user/login_failed.html", "Set-Cookie: logined=false;");
                    return;
                }
                response302Header(dos, "/index.html", "Set-Cookie: logined=true;");
                return;
            }
            if (memberService.isMemberListRequest(httpRequest)) {
                if (requstParser.isLoginCookie(httpRequest.getCookies())) {
                    System.out.println("isLoginCookie!!!!");
                    List<User> members = memberService.getAllMembers();
                    Template template = handlebars.compile("user/list");

                    byte[] body = template.apply(members).getBytes();
                    response200Header(dos, body.length, httpRequest.getMimeType().getType());
                    responseBody(dos, body);
                    return;
                }
                response302Header(dos, "/user/login.html", "Set-Cookie: logined=false;");
                return;
            }
            byte[] body = getBody(httpRequest.getPath());
            response200Header(dos, body.length, httpRequest.getMimeType().getType());
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(String requestPath) {
        logger.debug("requestPath : {}", requestPath);
        try {
            if ("/".equals(requestPath)) {
                return DEFAULT_RESPONSE.getBytes();
            }
            return FileIoUtils.loadFileFromClasspath(RequestPathUtils.getResourcePath(requestPath));
        } catch (IOException | URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return DEFAULT_RESPONSE.getBytes();
    }

    private void printRequestHeaders(List<HttpHeader> headers) {
        headers.forEach(header -> logger.debug(header.toString()));
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            if (!contentType.isEmpty()) {
                dos.writeBytes("Content-Type: " + contentType + "\r\n");
            }
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            if (!cookie.isEmpty()) {
                dos.writeBytes(cookie + " Path=/" + "\r\n");
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

package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.HttpHeader;
import domain.HttpRequest;
import domain.HttpResponse;
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
            HttpResponse httpResponse = new HttpResponse(dos);
            if (memberService.isJoinReq(httpRequest)) {
                memberService.joinMember(httpRequest.getRequestParam());
                httpResponse.response302Header("/index.html", "");
                return;
            }
            if (memberService.isLoginReq(httpRequest)) {
                boolean isLogin = memberService.memberLogin(httpRequest.getRequestParam());
                if (!isLogin) {
                    httpResponse.response302Header("/user/login_failed.html", "Set-Cookie: logined=false;");
                    return;
                }
                httpResponse.response302Header("/index.html", "Set-Cookie: logined=true;");
                return;
            }
            if (memberService.isMembersReq(httpRequest)) {
                if (requstParser.isLoginCookie(httpRequest.getCookies())) {
                    List<User> members = memberService.getAllMembers();
                    Template template = handlebars.compile("user/list");

                    byte[] body = template.apply(members).getBytes();
                    httpResponse.response200Header(body, httpRequest);
                    return;
                }
                httpResponse.response302Header("/user/login.html", "Set-Cookie: logined=false;");
                return;
            }
            httpResponse.response200Header(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequestHeaders(List<HttpHeader> headers) {
        headers.forEach(header -> logger.debug(header.toString()));
    }
}

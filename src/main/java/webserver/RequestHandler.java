package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.HttpHeader;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HandlebarTemplateException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MemberService;
import utils.HttpRequstParser;

import java.io.*;
import java.net.Socket;
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
            HttpResponse response = new HttpResponse(dos, httpRequest);
            memberAction(httpRequest, response, requstParser);
            response.response200OK();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new HandlebarTemplateException("템플릿 지정이 잘못되었습니다.");
        }
    }

    private void printRequestHeaders(List<HttpHeader> headers) {
        headers.forEach(header -> logger.debug(header.toString()));
    }

    private void memberAction(HttpRequest httpRequest, HttpResponse response, HttpRequstParser requstParser) throws IOException {
        if (memberService.isJoinReq(httpRequest)) {
            memberService.joinMember(httpRequest.getParameter());
            response.response302Redirect("/index.html", false);
        }
        if (memberService.isLoginReq(httpRequest)) {
            boolean isLogin = memberService.memberLogin(httpRequest.getParameter());
            if (!isLogin) {
                response.response302Redirect("/user/login_failed.html", false);
            }
            response.response302Redirect("/index.html", true);
        }
        if (memberService.isMembersReq(httpRequest)) {
            if (requstParser.isLoginCookie(httpRequest.getCookies())) {
                List<User> members = memberService.getAllMembers();
                Template template = handlebars.compile("user/list");
                byte[] body = template.apply(members).getBytes();
                response.response200OK(body);
            }
            response.response302Redirect("/user/login.html", false);
        }
    }
}

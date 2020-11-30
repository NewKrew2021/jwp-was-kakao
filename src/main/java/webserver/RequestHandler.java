package webserver;

import domain.HttpMethod;
import domain.HttpRequestHeader;
import model.User;
import service.MemberService;
import utils.HttpRequstParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_RESPONSE = "Hello World";
    private static final MemberService memberService = new MemberService();
    private Socket connection;

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
            List<HttpRequestHeader> headers = requstParser.getRequestHeaders();
            printRequestHeaders(headers); //헤더 출력

            DataOutputStream dos = new DataOutputStream(out);
            if (memberService.isMemberJoinRequst(requstParser)) {
                memberService.joinMember(getMemberInfo(requstParser, headers));
                response302Header(dos, "/index.html", "");
                return;
            }
            if (memberService.memberLoginRequest(requstParser)) {
                boolean isLogin = memberService.memberLogin(getMemberInfo(requstParser, headers));
                if (!isLogin) {
                    response302Header(dos, "/user/login_failed.html", "Set-Cookie: logined=false;");
                    return;
                }
                response302Header(dos, "/index.html", "Set-Cookie: logined=true;");
                return;
            }
            byte[] body = getBody(requstParser);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(HttpRequstParser requstParser) {
        final String requestPath = requstParser.getRequestPath();
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

    private void printRequestHeaders(List<HttpRequestHeader> headers) {
        headers.forEach(header -> logger.debug(header.toString()));
    }

    protected Map<String, String> getMemberInfo(HttpRequstParser requstParser, List<HttpRequestHeader> headers) {
        String requestBody = requstParser.getRequestBody(headers);
        return requstParser.getRequstParameters(requestBody);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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

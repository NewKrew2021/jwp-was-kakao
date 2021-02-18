package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import service.LoginService;
import javax.security.auth.login.LoginException;

public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";
    private static final String INDEX_HTML = "/index.html";
    private final LoginService loginService = new LoginService();
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String sessionId = loginService.login(httpRequest);
            httpResponse
                    .addRedirectionLocationHeader(INDEX_HTML)
                    .addSetCookieHeaderSessionId(sessionId)
                    .send(HttpResponseStatusCode.FOUND)
                    .build();
        } catch (LoginException e) {
            httpResponse
                    .addRedirectionLocationHeader(LOGIN_FAIL_PAGE)
                    .send(HttpResponseStatusCode.FOUND)
                    .build();
            logger.error(e.getMessage());
        }
    }

}

package controller;

import exception.NotExistPathException;
import model.Login;
import org.springframework.http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {

    private final static String COOKIE_REQUEST = "Cookie";


    protected static final String INDEX_HTML = "/index.html";
    protected static final String USER_LOGIN_URL = "/user/login.html";
    protected static final String LOGIN_FAIL_PAGE = "/user/login_failed.html";

    protected Login login = new Login();

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

        login.isRequestLogin(httpRequest.getHeader(COOKIE_REQUEST));

        if (httpRequest.getMethod().equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }

        if (httpRequest.getMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new NotExistPathException();
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new NotExistPathException();
    }

}



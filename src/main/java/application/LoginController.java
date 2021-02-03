package application;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

public class LoginController extends AbstractController {

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_PASSWORD = "password";
    public static final String INDEX_HTML = "/index.html";
    public static final String KEY_SET_COOKIE = "Set-Cookie";
    public static final String VALUE_LOGINED_TRUE = "logined=true; Path=/";
    public static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    public static final String VALUE_LOGINED_FALSE = "logined=false;";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = DataBase.findUserById(httpRequest.getParameter(KEY_USER_ID));
        // 아이디가 없습니다.
        boolean isLoginSuccess = user.validatePassword(httpRequest.getParameter(KEY_PASSWORD));
        // 패스워드가 잘못됐습니다.
        if (isLoginSuccess) {
            httpResponse.sendRedirect(INDEX_HTML);
            httpResponse.addHeader(KEY_SET_COOKIE, VALUE_LOGINED_TRUE);
            return;
        }
        httpResponse.sendRedirect(LOGIN_FAILED_HTML);
        httpResponse.addHeader(KEY_SET_COOKIE, VALUE_LOGINED_FALSE);
    }
}

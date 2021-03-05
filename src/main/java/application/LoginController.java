package application;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;
import service.MemberService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LoginController extends AbstractController {

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_PASSWORD = "password";
    public static final String INDEX_HTML = "/index.html";
    public static final String KEY_SET_COOKIE = "Set-Cookie";
    public static final String VALUE_LOGINED_TRUE = "logined=true; Path=/; expires=";
    public static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    public static final int COOKIE_DURATION = 10;

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    private String cookieWithExpireTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime
                .now(ZoneOffset.UTC)
                .plus(Duration.ofSeconds(COOKIE_DURATION));
        String expiresTime = DateTimeFormatter.RFC_1123_DATE_TIME.format(offsetDateTime);
        return VALUE_LOGINED_TRUE + expiresTime;
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (memberService.isExist(httpRequest)) {
            User user = DataBase.findUserById(httpRequest.getParameter(KEY_USER_ID));
            boolean isLoginSuccess = user.validatePassword(httpRequest.getParameter(KEY_PASSWORD));
            if (isLoginSuccess) {
                httpResponse.addHeader(KEY_SET_COOKIE, cookieWithExpireTime());
                httpResponse.sendRedirect(INDEX_HTML);
                return;
            }
        }
        httpResponse.sendRedirect(LOGIN_FAILED_HTML);
    }
}

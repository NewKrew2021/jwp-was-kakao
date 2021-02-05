package exceptions;

public class LoginFailException extends RuntimeException{
    private static final String LOGIN_FAIL_MESSAGE = "로그인을 할 수 없습니다.";

    public LoginFailException(){
        super(LOGIN_FAIL_MESSAGE);
    }
}

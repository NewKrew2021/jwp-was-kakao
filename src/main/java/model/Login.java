package model;

public class Login {

    boolean login = false;
    private final static String LOGIN_TRUE = "logined=true";

    public void isRequestLogin(String loginCookie) {
        if (loginCookie != null && loginCookie.equals(LOGIN_TRUE)) {
            login = true;
        }
    }

    public boolean isLogin() {
        return login;
    }

    public void updateLoginState(String userID, String password , String comparePassword) {
        if( userID == null) {
            return;
        }
        login = password.equals(comparePassword);
    }

}

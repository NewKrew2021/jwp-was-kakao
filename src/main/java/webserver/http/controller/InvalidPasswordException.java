package webserver.http.controller;

import java.text.MessageFormat;

public class InvalidPasswordException extends LoginException {
    public InvalidPasswordException(String userId) {
        super(MessageFormat.format("패스워드가 일치하지 않습니다. (userId:{0})", userId));
    }
}

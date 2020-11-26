package webserver.http.controller;

import java.text.MessageFormat;

public class NotExistUserException extends AuthenticationException {
    public NotExistUserException(String userId) {
        super(MessageFormat.format("존재하지 않는 유저({0}) 입니다.", userId));
    }
}

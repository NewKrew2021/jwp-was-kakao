package apps.slipp.service;

import apps.slipp.model.User;

import java.text.MessageFormat;

public class UserAlreadySignUpException extends RuntimeException {
    public UserAlreadySignUpException(User user) {
        super(MessageFormat.format("'{0}' ID 는 이미 가입되어 있습니다.", user.getUserId()));
    }
}

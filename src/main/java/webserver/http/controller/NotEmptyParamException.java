package webserver.http.controller;

import java.text.MessageFormat;

public class NotEmptyParamException extends RuntimeException {
    public NotEmptyParamException(String notEmptyParam) {
        super(MessageFormat.format("입력값이 필요한 파라미터({0}) 입니다.", notEmptyParam));
    }
}

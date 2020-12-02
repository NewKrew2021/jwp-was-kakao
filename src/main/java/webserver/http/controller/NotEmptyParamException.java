package webserver.http.controller;

import webserver.http.InvalidHttpRequestMessageException;

import java.text.MessageFormat;

public class NotEmptyParamException extends InvalidHttpRequestMessageException {
    public NotEmptyParamException(String notEmptyParam) {
        super(MessageFormat.format("입력값이 필요한 파라미터({0}) 입니다.", notEmptyParam));
    }
}

package webserver.http;

import java.text.MessageFormat;

public class MissingRequiredParamException extends InvalidHttpRequestParameterException {

    public MissingRequiredParamException(String requiredParam) {
        super(MessageFormat.format("필수 파라미터({0})가 누락되었습니다.", requiredParam));
    }
}

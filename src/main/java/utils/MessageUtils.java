package utils;

public class MessageUtils {

    private MessageUtils() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static final String UTILITY_CLASS = "Utility class";

    public static final String ILLEGAL_PATH_STATE = "정의되지 않은 path입니다.";

    public static final String PARAM_VALUE_IS_EMPTY = "파라메터 문자열이 정상적이지 않은 빈 값입니다.";
    public static final String INVALID_USER_PARAM = "회원가입시 입력값 중 빈값이 있습니다.";
    public static final String INVALID_USER_EMAIL = "이메일 형식이 잘못되었습니다.";
}

package utils;

public class MessageUtils {

    private MessageUtils() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static final String UTILITY_CLASS = "Utility class";

    public static final String INVALID_PARAM_VALUE = "파라메터 문자열이 정상적이지 않은 빈 값입니다.";
}

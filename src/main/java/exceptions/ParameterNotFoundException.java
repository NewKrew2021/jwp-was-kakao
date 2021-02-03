package exceptions;

public class ParameterNotFoundException extends RuntimeException {
    private static String PARAMETER_NOT_FOUND_MESSAGE = "파라미터를 찾을 수 없습니다.";

    public ParameterNotFoundException() {
        super(PARAMETER_NOT_FOUND_MESSAGE);
    }
}

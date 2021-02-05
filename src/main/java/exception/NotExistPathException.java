package exception;

public class NotExistPathException extends RuntimeException  {

    private static final String NOT_EXIST_PATH_EXCEPTION = "존재하지 않는 페이지 입니다.";

    public NotExistPathException() {
        super(NOT_EXIST_PATH_EXCEPTION);
    }

}

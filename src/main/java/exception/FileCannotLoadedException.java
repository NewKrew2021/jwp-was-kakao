package exception;

public class FileCannotLoadedException extends RuntimeException {

    public FileCannotLoadedException() {
        super("파일을 로드하는 중 에러가 발생했습니다");
    }
}

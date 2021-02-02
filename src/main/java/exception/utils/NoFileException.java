package exception.utils;

public class NoFileException extends Exception {
    public NoFileException() {
        super("파일을 찾을 수 없습니다.");
    }
}

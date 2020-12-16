package utils.io;

public class FileReadException extends RuntimeException {
    private static final String MESSAGE = "파일을 읽을 수 없습니다.";

    public FileReadException() {
        super(MESSAGE);
    }
}

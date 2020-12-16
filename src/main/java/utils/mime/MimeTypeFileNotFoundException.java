package utils.mime;

public class MimeTypeFileNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Mime Type 파일을 찾을 수 없습니다.";

    public MimeTypeFileNotFoundException() {
        super(MESSAGE);
    }
}

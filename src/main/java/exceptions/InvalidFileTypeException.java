package exceptions;

public class InvalidFileTypeException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "잘못된 file type 입니다. : ";

    public InvalidFileTypeException(String fileType) {
        super(MESSAGE_FORMAT + fileType);
    }

}

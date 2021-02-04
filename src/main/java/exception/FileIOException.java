package exception;

import java.io.IOException;

public class FileIOException extends IOException {

    public FileIOException(String filePath) {
        super("FileIOException ocuur, File path : " + filePath);
    }
}

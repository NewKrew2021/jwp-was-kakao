package utils.io;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    public static String readData(BufferedReader br, int contentLength) {
        char[] body = new char[contentLength];
        try {
            br.read(body, 0, contentLength);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new FileReadException();
        }
        return String.copyValueOf(body);
    }
}

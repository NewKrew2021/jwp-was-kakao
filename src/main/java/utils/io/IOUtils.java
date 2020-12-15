package utils.io;

import java.io.BufferedReader;

public class IOUtils {
    public static String readData(BufferedReader br, int contentLength) {
        try {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            return String.copyValueOf(body);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new FileReadException();
        }
    }
}

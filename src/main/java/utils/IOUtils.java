package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static final String NEW_LINE = System.lineSeparator();

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader bufferedReader, int contentLength) {
        try {
            char[] body = new char[contentLength];
            bufferedReader.read(body, 0, contentLength);
            return String.copyValueOf(body);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> readUntilEmptyLine(BufferedReader bufferedReader) {
        try {
            List<String> texts = new ArrayList<>();
            String text = bufferedReader.readLine();
            while (text != null && !text.trim().isEmpty()) {
                texts.add(text);
                text = bufferedReader.readLine();
            }
            return texts;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

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
    public static String readData(BufferedReader br, int contentLength) {
        try {
            char[] body = new char[contentLength];
            br.read(body, 0, contentLength);
            return String.copyValueOf(body);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> readUntilEmptyLine(BufferedReader in) {
        List<String> texts = new ArrayList<>();

        try {
            readUntilEmptyLine(in, texts);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return texts;
    }

    private static void readUntilEmptyLine(BufferedReader bufferedReader, List<String> texts) throws IOException {
        String text = bufferedReader.readLine();
        while (text != null && !text.trim().isEmpty()) {
            texts.add(text);
            text = bufferedReader.readLine();
        }
    }
}

package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readRequest(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        List<String> texts = new ArrayList<>();

        try {
            readRequest(bufferedReader, texts);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return texts;
    }

    private static void readRequest(BufferedReader bufferedReader, List<String> texts) throws IOException {
        String text;
        while ((text = bufferedReader.readLine()) != null) {
            if ("".equals(text)) {
                break;
            }
            texts.add(text);
        }
    }
}

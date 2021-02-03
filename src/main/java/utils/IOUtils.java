package utils;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {
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

    public static String readRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        List<String> lines = new ArrayList<>();
        readHead(br, lines);
        readBody(br, lines);
        decoding(lines);

        return lines.stream()
                .collect(Collectors.joining("\n"));
    }

    private static void readHead(BufferedReader br, List<String> lines) throws IOException {
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            lines.add(line);
        }
    }

    private static void readBody(BufferedReader br, List<String> lines) throws IOException {
        int contentLength = getContentLength(lines);
        if (getContentLength(lines) > 0) {
            lines.add("\n" + readData(br, contentLength));
        }
    }

    private static int getContentLength(List<String> lines) {
        return lines.stream()
                .filter(it -> it.split(": ", 2)[0].equals("Content-Length"))
                .findAny()
                .map(contentLengthLine -> Integer.parseInt(contentLengthLine.split(": ", 2)[1]))
                .orElse(0);
    }

    private static void decoding(List<String> lines) throws UnsupportedEncodingException {
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, URLDecoder.decode(lines.get(i), "utf-8"));
        }
    }
}

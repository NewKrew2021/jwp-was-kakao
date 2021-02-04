package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static List<String> readUntilDelimiter(BufferedReader br, String delimiter) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = readLine(br);
        while (line != null && !line.equals(delimiter)) {
            lines.add(line);
            line = readLine(br);
        }

        return lines;
    }

    public static String readLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line != null) {
            return URLDecoder.decode(line, "UTF-8");
        }
        return null;
    }
}

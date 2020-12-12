package webserver.request;

import org.springframework.util.ObjectUtils;
import utils.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestReader {
    public static HttpRequest read(BufferedReader bufferedReader) {
        HttpRequest request = HttpRequest.of(readRequestHeader(bufferedReader));
        request.setBodyParams(readRequestBody(bufferedReader, request.getHeader().getContentLength()));
        return request;
    }

    private static List<String> readRequestHeader(BufferedReader bufferedReader) {
        List<String> lines = new ArrayList<>();
        String line = readLine(bufferedReader);
        while (!ObjectUtils.isEmpty(line)) {
            lines.add(line);
            line = readLine(bufferedReader);
        }
        lines.forEach(System.out::println);
        System.out.println();
        return lines;
    }

    private static String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String readRequestBody(BufferedReader bufferedReader, Integer contentLength) {
        return Optional.ofNullable(contentLength)
                .map(length -> IOUtils.readData(bufferedReader, length))
                .orElse("");
    }

}

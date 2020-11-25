package webserver.request;

import org.springframework.util.ObjectUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestReader {
    public static Request read(BufferedReader bufferedReader) throws IOException {
        RequestHeader requestHeader = RequestHeader.of(readRequestHeader(bufferedReader));
        print(requestHeader);
        return new Request(requestHeader, getRequestBody(bufferedReader, requestHeader));
    }

    private static String getRequestBody(BufferedReader bufferedReader, RequestHeader requestHeader) {
        return Optional.ofNullable(requestHeader.getContentLength())
                .map(contentLength -> readRequestBody(bufferedReader, contentLength))
                .orElse("");
    }

    private static String readRequestBody(BufferedReader bufferedReader, Integer contentLength) {
        try {
            return IOUtils.readData(bufferedReader, contentLength);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void print(RequestHeader requestHeader) {
        System.out.println(requestHeader);
        System.out.println();
    }

    private static List<String> readRequestHeader(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!ObjectUtils.isEmpty(line)) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }

}

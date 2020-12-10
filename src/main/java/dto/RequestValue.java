package dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestValue {
    private static final String REGEX_COLON = ":";
    private static final String CONTENT_LENGTH_FIELD = "Content-Length";

    private static final Logger logger = LoggerFactory.getLogger(RequestValue.class);

    private final List<String> header;
    private final String body;

    public RequestValue(List<String> header, String body) {
        this.header = header;
        this.body = body;
    }

    public static RequestValue of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        List<String> header = parseHeader(br);
        String body = IOUtils.readData(br, getBodySize(header));

        return new RequestValue(header, body);
    }

    public static List<String> parseHeader(BufferedReader bufferedReader) throws IOException {
        List<String> data = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (line == null) break;

            data.add(line);
            line = bufferedReader.readLine();
        }
        return data;
    }

    public static int getBodySize(List<String> header) {
        return header.stream()
                .filter(RequestValue::filterContent)
                .map(RequestValue::parseContentSize)
                .map(Integer::parseInt)
                .findAny()
                .orElse(0);
    }

    private static boolean filterContent(String header) {
        return header.contains(CONTENT_LENGTH_FIELD);
    }

    private static String parseContentSize(String header) {
        return header.split(REGEX_COLON)[1].trim();
    }


    public void printRequest() {
        header.forEach(logger::info);
    }

    public List<String> getHeader() {
        return header;
    }

    public String getBody() {
        return this.body;
    }

    public String getFirstReqeustLine() {
        return header.get(0);
    }
}

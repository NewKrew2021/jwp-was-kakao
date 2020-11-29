package dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DecodeUtils;
import utils.IOUtils;
import validator.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestValue {
    private static final String QUESTION_MARK = "?";

    private static final String REGEX_BLANK = " ";
    private static final String REGEX_QUESTION_MARK = "\\?";
    private static final String REGEX_QUESTION_MARK_AND_PERIOD = "[?.]";
    private static final String REGEX_COLON = ":";

    private static final String CONTENT_LENGTH_FIELD = "Content-Length";

    private static final String POST_METHOD = "POST";
    private static final String GET_METHOD = "GET";

    private static final Logger logger = LoggerFactory.getLogger(RequestValue.class);

    private List<String> header;
    private String body;

    public RequestValue(List<String> header, String body) {
        this.header = header;
        this.body = body;
    }

    public static RequestValue of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        List<String> header = parseHeader(br);
        String body = getBody(br, header);

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

    public static String getBody(BufferedReader br, List<String> header) throws IOException {
        return IOUtils.readData(br, getBodySize(header));
    }

    public static int getBodySize(List<String> header) {
        Optional<Integer> any = header.stream()
                .filter(RequestValue::filterContent)
                .map(RequestValue::parseContentSize)
                .map(Integer::parseInt)
                .findAny();

        return any.orElse(0);
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

    public String getFirstReqeustLine() {
        return header.get(0);
    }

    public String getMethod() {
        return getFirstReqeustLine().split(REGEX_BLANK)[0];
    }

    public String getURL() {
        return getFirstReqeustLine().split(REGEX_BLANK)[1];
    }

    public String getURLPath() {
        return getURL().split(REGEX_QUESTION_MARK_AND_PERIOD)[0];
    }


    public Optional<String> getParams() {
        switch (getMethod()) {
            case POST_METHOD:
                return getPostParams();
            case GET_METHOD:
                return getGetParams();
        }
        return Optional.empty();
    }

    private Optional<String> getGetParams() {
        if (!getURL().contains(QUESTION_MARK)) {
            return Optional.empty();
        }
        String paramString = getURL().split(REGEX_QUESTION_MARK)[1];
        return Optional.of(DecodeUtils.decodeUTF8(paramString));
    }

    private Optional<String> getPostParams() {
        if (InputValidator.isValidEmpty(body)) {
            return Optional.of(DecodeUtils.decodeUTF8(body));
        }
        return Optional.empty();
    }
}

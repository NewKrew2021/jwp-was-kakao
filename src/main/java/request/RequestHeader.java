package request;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HEADER_DISTINGUISH_REGEX = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    Map<String, String> header;

    private RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public static RequestHeader of(BufferedReader br, Logger logger) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        String tempLine;
        while (!(tempLine = br.readLine()).equals("")) {
            String[] splitTempLine = tempLine.split(HEADER_DISTINGUISH_REGEX);
            requestHeader.put(splitTempLine[KEY_INDEX].trim(), splitTempLine[VALUE_INDEX].trim());
            logger.debug("header: {}", tempLine);
        }
        return new RequestHeader(requestHeader);
    }

    public Optional<Integer> getContentLength() {
        if (header.containsKey(CONTENT_LENGTH)) {
            return Optional.of(Integer.parseInt(header.get(CONTENT_LENGTH)));
        }
        return Optional.empty();
    }

    public Optional<String> getHeaderValue(String key) {
        if (header.containsKey(key)) {
            return Optional.of(header.get(key));
        }
        return Optional.empty();
    }

}

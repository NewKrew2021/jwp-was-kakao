package request;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    Map<String, String> header;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }

    public static RequestHeader of(BufferedReader br, Logger logger) throws IOException {
        Map<String, String> requestHeader = new HashMap<>();
        String tempLine;
        while(!(tempLine = br.readLine()).equals("")){
            String[] splitTempLine = tempLine.split(":");
            requestHeader.put(splitTempLine[0].trim(), splitTempLine[1].trim());
            logger.debug("header: {}", tempLine);
        }
        return new RequestHeader(requestHeader);
    }

    public Optional<Integer> getContentLength() {
        if (header.containsKey("Content-Length")) {
            return Optional.of(Integer.parseInt(header.get("Content-Length")));
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

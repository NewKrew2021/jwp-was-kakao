package utils;

import annotation.web.RequestMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class HeaderParser {
    private String request;

    public HeaderParser(InputStream in) throws IOException {
        this.request = IOUtils.buildString(in);
    }

    public RequestMethod getRequestMethod() {
        return Arrays.stream(request.split("\\r?\\n"))
                .map(it -> it.split(" ")[0])
                .map(RequestMethod::getMethod)
                .filter(it -> it != RequestMethod.NOTHING)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}

package webserver.model;

import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static final Pattern requestLinePattern = Pattern.compile(
            "(?<method>[A-Z]+)" +         // request method
            " +(?<path>/[^?# ]+)" +        // resource path (only path part)
            "(?:\\?(?<query>[^# ]+))?" +  // query string (optional)
            "(?:#(?<hash>[^ ]+))?" +      // hash (optional)
            "(?: +(?<version>.*))?"       // version (optional)
    );

    private final String method;
    private final String path;
    private final String query;
    private final String version;

    public Request(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Matcher matcher = parseRequestLine(reader.readLine());
        method = matcher.group("method");
        path = matcher.group("path");
        query = matcher.group("query");
        version = matcher.group("version");
    }

    private Matcher parseRequestLine(String requestLine) {
        Matcher matcher;
        if (Objects.isNull(requestLine) || !(matcher = requestLinePattern.matcher(requestLine)).find()) {
            throw new IllegalArgumentException("Unexpected Request-Line message");
        }
        return matcher;
    }

    public byte[] getRequestedResource() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath("./templates" + path);
    }
}

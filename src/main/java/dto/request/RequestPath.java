package dto.request;

import exceptions.InvalidRequestPathException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestPath {

    private final String path;

    private static final Pattern pathPattern = Pattern.compile("(\\/{1}[^\\?\\=\\;\\:\\,\\/]*)+");

    public RequestPath(String path) {
        validate(path);
        this.path = path;
    }

    private void validate(String path) {
        Matcher matcher = pathPattern.matcher(path);
        if (!matcher.find()) {
            throw new InvalidRequestPathException(path);
        }
    }

    public String getPath() {
        return path;
    }
}

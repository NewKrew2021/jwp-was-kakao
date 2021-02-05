package webserver.domain;

public class URI {
    private static final String QUERY_DELIMITER = "\\?";
    private static final String BLANK = "";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private final String path;
    private final String queryString;

    public URI(String uri) {
        String[] uris = uri.split(QUERY_DELIMITER);
        path = uris[PATH_INDEX];
        queryString = makeQueryString(uris);
    }

    private String makeQueryString(String[] uris) {
        if (uris.length > 1) {
            return uris[QUERY_INDEX];
        }
        return BLANK;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}

package webserver.domain;

public class URI {
    public static final String QUESTION_MARK = "\\?";
    public static final String BLANK = "";
    private final String path;
    private final String queryString;

    public URI(String uri) {
        String[] uris = uri.split(QUESTION_MARK);
        path = uris[0];
        queryString = makeQueryString(uris);
    }

    private String makeQueryString(String[] uris) {
        if (uris.length > 1) {
            return uris[1];
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

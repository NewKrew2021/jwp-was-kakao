package webserver.http;

/**
 * https://tools.ietf.org/html/rfc2616#section-7.1
 */
public enum EntityHeader implements HeaderConstant{
    ContentLength("Content-Length"),
    ContentType("Content-Type");

    private final String headerName;

    EntityHeader(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getHeaderName() {
        return headerName;
    }
}

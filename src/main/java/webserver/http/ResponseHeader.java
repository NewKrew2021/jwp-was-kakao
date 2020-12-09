package webserver.http;

/**
 * https://tools.ietf.org/html/rfc2616#section-6.2
 */
public enum ResponseHeader implements HeaderConstant {
    SetCookie("Set-Cookie"),
    Location("Location");

    private String headerName;

    ResponseHeader(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getHeaderName() {
        return headerName;
    }
}

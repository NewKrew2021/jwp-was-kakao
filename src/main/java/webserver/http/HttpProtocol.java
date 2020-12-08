package webserver.http;

public class HttpProtocol {

    String protocol;

    public HttpProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return protocol;
    }
}

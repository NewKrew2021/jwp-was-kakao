package webserver.http;

public class HttpProtocol {

    String protocol;

    private HttpProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static HttpProtocol from(String protocol){
        return new HttpProtocol(protocol);
    }

    @Override
    public String toString() {
        return protocol;
    }
}

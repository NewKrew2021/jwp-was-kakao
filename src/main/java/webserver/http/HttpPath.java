package webserver.http;

public class HttpPath {
    String path;

    public HttpPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}

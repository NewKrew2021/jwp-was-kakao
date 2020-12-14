package webserver.http;

public class HttpPath {
    String path;

    private HttpPath(String path) {
        this.path = path;
    }

    public static HttpPath from(String path){
        return new HttpPath(path);
    }

    @Override
    public String toString() {
        return path;
    }
}

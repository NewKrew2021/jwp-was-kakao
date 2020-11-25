package webserver;

public class Cookie {

    private String content;
    private String path;

    public Cookie(String content, String path) {
        this.content = content;
        this.path = path;
    }

    public static Cookie login() {
        return new Cookie("logined=true", "/");
    }

    public static Cookie logout() {
        return new Cookie("logined=false", "/");
    }

    public String getContent() {
        return content;
    }

    public String getPath() {
        return path;
    }

    public boolean contains(String content) {
        return this.content.contains(content);
    }

    public static Cookie of(String line) {
        return new Cookie(line, null);
    }
}

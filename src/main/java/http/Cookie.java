package http;

public class Cookie {
    private String name;
    private String value;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String cookie) {
        String[] parsedCookie = cookie.split("=");
        this.name = parsedCookie[0];
        this.value = parsedCookie[1];
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean matched(String name) {
        return this.name.equals(name);
    }

    public String toSetCookie() {
        String cookie = "Set-Cookie: " + name + "=" + value;
        if (path != null) {
            cookie += "; " + "Path=" + path;
        }
        return cookie;
    }
}

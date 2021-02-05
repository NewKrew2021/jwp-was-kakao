package http;

public class Cookie {
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String PATH = "Path";
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
        String cookie = String.format("%s: %s=%s", SET_COOKIE, name, value);
        if (path != null) {
            cookie += String.format("; %s=%s", PATH, path);
        }
        return cookie;
    }
}

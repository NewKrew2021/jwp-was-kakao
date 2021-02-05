package webserver.domain;

public class Cookie {
    private String name;
    private String value;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean match(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name + "=" + value + "; " + "Path = " + path;
    }
}

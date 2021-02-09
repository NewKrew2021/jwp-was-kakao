package webserver.domain;


import java.util.Objects;

public class Cookie {
    public static final String PATH = "Path";
    private String name;
    private String value;
    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String cookieString) {
        String[] splitData = cookieString.split("=");
        this.name = splitData[0];
        this.value = splitData[1];
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) && Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpHeader.SET_COOKIE)
                .append(": ")
                .append(name)
                .append("=")
                .append(value)
                .append("; ");
        if (path != null) {
            sb.append(PATH)
                    .append("=")
                    .append(path);
        }
        return sb.toString();
    }
}

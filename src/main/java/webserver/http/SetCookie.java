package webserver.http;

public class SetCookie {

    private final String name;
    private final String value;

    private String path;

    public SetCookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        StringBuilder sb =  new StringBuilder();
        sb.append(String.format("%s=%s", name, value));

        if (path != null) {
            sb.append(String.format("; Path=%s", path));
        }

        return sb.toString();
    }


}

package request;

public class Cookie {
    private String name;
    private String value;
    private String path;

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public static Cookie from(String line) {
        String[] splitLine = line.split("=");
        return new Cookie(splitLine[0].trim(), splitLine[1].trim(), null);
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
}

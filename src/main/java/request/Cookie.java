package request;

public class Cookie {
    private String name;
    private String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Cookie from(String line) {
        String[] splitLine = line.split("=");
        return new Cookie(splitLine[0].trim(), splitLine[1].trim());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}

package webserver.domain;

public class Cookie {
    private String name;
    private String value;
    private String pathName;
    private String pathValue;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String name, String value, String pathName, String pathValue) {
        this.name = name;
        this.value = value;
        this.pathName = pathName;
        this.pathValue = pathValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPathName() {
        return pathName;
    }

    public String getPathValue() {
        return pathValue;
    }

    @Override
    public String toString() {
        return name+"="+value+"; "+pathName+"="+pathValue;
    }
}

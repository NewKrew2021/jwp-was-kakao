package http;

public enum StatusCode {
    OK(200, "OK"), FOUND(302, "Found");

    private final int number;
    private final String name;

    StatusCode(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + " " + name;
    }
}

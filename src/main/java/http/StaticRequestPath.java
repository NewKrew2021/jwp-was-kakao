package http;

public enum StaticRequestPath {
    CSS("/css"), FONTS("/fonts"), IMAGES("/images"), JS("/js");

    private String path;

    StaticRequestPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

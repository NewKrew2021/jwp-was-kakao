package model;

public class File {
    private String extenstion;
    private String location;
    private String contentType;

    public File(String extenstion, String location, String contentType) {
        this.extenstion = extenstion;
        this.location = location;
        this.contentType = contentType;
    }

    public String getLocation() {
        return location;
    }

    public String getContentType() {
        return contentType;
    }
}

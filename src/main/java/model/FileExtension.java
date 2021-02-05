package model;

public class FileExtension {
    private String extension;
    private String location;
    private String contentType;

    public FileExtension(String extension, String location, String contentType) {
        this.extension = extension;
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

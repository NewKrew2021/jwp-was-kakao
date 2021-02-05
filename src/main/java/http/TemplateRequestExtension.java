package http;

public enum TemplateRequestExtension {
    HTML(".html"), ICO(".ico");

    private String extension;

    TemplateRequestExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}

package webserver.request;

public class RequestPath {
    private static final String STATIC_PATH_PREFIX = "./static";
    private static final String TEMPLATE_PATH_PREFIX = "./templates";
    private static final String CSS_PREFIX = "/css";
    private static final String JS_PREFIX = "/js";
    private static final String FONT_PREFIX = "/fonts";
    private static final String IMAGE_PREFIX = "/images";
    private static final String FAVICON_SUFFIX = ".ico";
    private static final String HTML_SUFFIX = ".html";

    private final String path;

    public RequestPath(String path) {
        this.path = path;
    }

    public boolean requiresFile() {
        return requiresStaticResource() || requiresTemplate();
    }

    public boolean startsWith(String prefix) {
        return path.startsWith(prefix);
    }

    public boolean requiresCss() {
        return startsWith(CSS_PREFIX);
    }

    public String asFilePath() {
        if (requiresStaticResource()) {
            return STATIC_PATH_PREFIX + path;
        }
        if (requiresTemplate()) {
            return TEMPLATE_PATH_PREFIX + path;
        }
        return path;
    }

    public String asUrl() {
        return path;
    }

    private boolean requiresTemplate() {
        return path.endsWith(FAVICON_SUFFIX) ||
                path.endsWith(HTML_SUFFIX);
    }

    private boolean requiresStaticResource() {
        return path.startsWith(JS_PREFIX) ||
                requiresCss() ||
                path.startsWith(FONT_PREFIX) ||
                path.startsWith(IMAGE_PREFIX);
    }

}

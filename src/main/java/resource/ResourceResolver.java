package resource;

public class ResourceResolver {
    private static final String STATIC_PATH_PREFIX = "./static";
    private static final String TEMPLATE_PATH_PREFIX = "./templates";
    private static final String CSS_PREFIX = "/css";
    private static final String JS_PREFIX = "/js";
    private static final String FONT_PREFIX = "/fonts";
    private static final String IMAGE_PREFIX = "/images";
    private static final String FAVICON_SUFFIX = ".ico";
    private static final String HTML_SUFFIX = ".html";

    public static String resolve(String path) {
        if (requiresStaticResource(path)) {
            return STATIC_PATH_PREFIX + path;
        }
        if (requiresTemplate(path)) {
            return TEMPLATE_PATH_PREFIX + path;
        }
        return path;
    }

    private static boolean requiresTemplate(String path) {
        return path.endsWith(FAVICON_SUFFIX) ||
                path.endsWith(HTML_SUFFIX);
    }

    private static boolean requiresStaticResource(String path) {
        return path.startsWith(JS_PREFIX) ||
                path.startsWith(CSS_PREFIX) ||
                path.startsWith(FONT_PREFIX) ||
                path.startsWith(IMAGE_PREFIX);
    }

}

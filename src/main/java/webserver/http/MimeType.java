package webserver.http;

import java.util.Arrays;
import java.util.List;

/**
 * http://www.iana.org/assignments/media-types/media-types.xhtml
 */
public enum MimeType {
    TEXT_HTML("text/html", Arrays.asList("htm", "html")),
    TEXT_CSS("text/css", Arrays.asList("css")),
    APPLICATION_JS("application/js", Arrays.asList("js"));

    private final String mimeType;
    private final List<String> extenstions;

    MimeType(String mimeType, List<String> extensions) {
        this.mimeType = mimeType;
        this.extenstions = extensions;
    }

    @Override
    public String toString() {
        return mimeType;
    }

    public static MimeType fromExtenstion(String extenstion) {
        return Arrays.stream(MimeType.values())
                .filter(it -> it.extenstions.contains(extenstion))
                .findFirst()
                .orElse(null);
    }

}

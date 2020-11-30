package webserver;

import com.google.common.collect.Sets;
import utils.FileIoUtils;

class StaticContentController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    @Override
    public Response execute(HttpRequest httpRequest) throws Exception {
        String requestURI = httpRequest.getRequestURI();
        byte[] body = FileIoUtils.loadFileFromClasspath(getBasePath(requestURI) + requestURI);
        Response response = new Response();
        response.setBody(body);
        response.setHeaders("Content-Type: " + MimeType.fromFileName(requestURI).getMimeTypeValue());
        return response;
    }

    public static String getBasePath(String requestURI) {
        return Sets.newHashSet("/css", "/js", "/fonts", "/images")
                .stream()
                .filter(requestURI::startsWith)
                .findAny()
                .map(path -> STATIC_PATH)
                .orElse(TEMPLATE_PATH);
    }
}

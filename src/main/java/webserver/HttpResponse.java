package webserver;

import error.ErrorResponse;
import model.MIME;
import model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String ERROR_PAGE = "<!DOCTYPE html><html lang=\"kr\"><body><h1>%s</h1><h2>%s</h2></body></html>";

    private final HttpStatus httpStatus;
    private final Map<String, String> header;
    private final byte[] body;

    private HttpResponse(HttpStatus httpStatus, Map<String, String> header, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = header;
        this.body = body;
    }

    private HttpResponse(HttpStatus httpStatus, Map<String, String> header) {
        this(httpStatus, header, new byte[0]);
    }

    private HttpResponse(HttpStatus httpStatus, byte[] body) {
        this(httpStatus, new HashMap<>(), body);
    }

    public static HttpResponse redirect(String url) {
        Map<String, String> header = new HashMap<>();
        header.put("Location", url);
        return new HttpResponse(HttpStatus.FOUND, header);
    }

    public static HttpResponse ok(Resource resource) {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", MIME.getContentType(resource.getExtension()));
        return new HttpResponse(HttpStatus.OK, header, resource.getBytes());
    }

    public static HttpResponse error(ErrorResponse errorResponse) {
        logger.error(errorResponse.getMessage());
        String errorPage = String.format(ERROR_PAGE, errorResponse.getHttpStatus().toString(), errorResponse.getMessage());
        HttpResponse response = new HttpResponse(errorResponse.getHttpStatus(), errorPage.getBytes(StandardCharsets.UTF_8));
        response.addHeader("Content-Type", MIME.getContentType("html"));
        return response;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public byte[] getBody() {
        return body;
    }

    public void logResponseHeader() {
        String message = "\n========= Response Header =========\n"
                + getHeader();
        logger.debug(message);
    }

    public String getHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION)
                .append(" ")
                .append(httpStatus.toString())
                .append("\r\n");
        for (Map.Entry<String, String> entry : header.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }
}

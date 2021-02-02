package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    protected static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final OutputStream outputStream;
    private final Map<String, String> header = new HashMap<>();
    private byte[] body = new byte[0];
    private HttpStatusCode statusCode;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void forward(String path) {
        forwardBody(ResourceLoader.getBytes(path));
    }

    public void forwardBody(byte[] body) {
        this.body = body;
        statusCode = HttpStatusCode.OK;
    }

    public void sendRedirect(String url) {
        addHeader("Location", url);
        statusCode = HttpStatusCode.FOUND;
    }

    public void badRequest() {
        statusCode = HttpStatusCode.BAD_REQUEST;
    }

    public void notFound() {
        statusCode = HttpStatusCode.NOT_FOUND;
    }

    public void unauthorized() {
        statusCode = HttpStatusCode.UNAUTHORIZED;
    }

    public void writeResponse() {
        try {
            writeResponseLine();
            writeResponseHeader();
            writeResponseBody();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponseLine() throws IOException {
        write(RequestHandler.HTTP_VERSION_NAME + " " + statusCode.toString() + "\r\n");
    }

    private void writeResponseHeader() throws IOException {
        for (Map.Entry<String, String> entry : header.entrySet()) {
            write(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        write("\r\n");
    }

    private void writeResponseBody() throws IOException {
        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    private void write(String message) throws IOException {
        logger.debug(message);
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
    }
}

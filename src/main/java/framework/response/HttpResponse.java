package framework.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.ParseUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static framework.common.HttpHeaders.*;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String KEY_VALUE_REGEX = ": ";
    private static final String NEW_LINE_PREFIX = "\r\n";

    private final DataOutputStream dos;

    private ResponseStatus status;
    private final Map<String, String> headers;
    private byte[] body;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        body = new byte[0];
        headers = new HashMap<>();
    }

    public static HttpResponse of(DataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        try {
            FileExtension fileExtension = FileExtension.getFileExtensionToExtension(ParseUtils.getExtension(path));
            body = FileIoUtils.loadFileFromClasspath(fileExtension.getFilePath() + path);
            headers.put(CONTENT_TYPE.getHeader(), fileExtension.getContentType());
            headers.put(CONTENT_LENGTH.getHeader(), String.valueOf(body.length));
            status = ResponseStatus.OK;
            response();
        } catch (FileSystemNotFoundException | NullPointerException e) {
            logger.error(e.getMessage());
            status = ResponseStatus.NOT_FOUND;
            response();
        }
    }

    public void sendRedirect(String path) {
        try {
            headers.put(LOCATION.getHeader(), path);
            status = ResponseStatus.FOUND;
            response();
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            status = ResponseStatus.NOT_FOUND;
            response();
        }
    }

    public void responseBody(byte[] body) {
        try {
            this.body = body;
            headers.put(CONTENT_LENGTH.getHeader(), String.valueOf(body.length));
            status = ResponseStatus.OK;
            response();
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            status = ResponseStatus.NOT_FOUND;
            response();
        }
    }

    public void badRequest() {
        try {
            status = ResponseStatus.BAD_REQUEST;
            response();
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            status = ResponseStatus.NOT_FOUND;
            response();
        }
    }

    private void response() {
        writeStatus();
        writeHeader();
        writeBody();
    }

    private void writeStatus() {
        try {
            String statusMessage = HTTP_VERSION + " " +
                    status.getCode() + " " +
                    status.getMessage() + " " +
                    NEW_LINE_PREFIX;
            dos.writeBytes(statusMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
            status = ResponseStatus.INTERNAL_SERVER_ERROR;
            response();
        }
    }

    private void writeHeader() {
        try {
            for (String key : headers.keySet()) {
                String header = key +
                        KEY_VALUE_REGEX +
                        headers.get(key) +
                        NEW_LINE_PREFIX;
                dos.writeBytes(header);
            }
            dos.writeBytes(NEW_LINE_PREFIX);
        } catch (IOException e) {
            logger.error(e.getMessage());
            status = ResponseStatus.INTERNAL_SERVER_ERROR;
            response();
        }
    }

    private void writeBody() {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
            status = ResponseStatus.INTERNAL_SERVER_ERROR;
            response();
        }
    }
}

package response;

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

import static common.HttpHeaders.*;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private static final String KEY_VALUE_REGEX = ": ";
    private static final String NEW_LINE_PREFIX = "\r\n";

    private Map<String, String> headers;
    private byte[] body;
    private final DataOutputStream dos;

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
            response(new Response200Status());
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            response(new Response404Status());
        }
    }

    public void sendRedirect(String path) {
        headers.put(LOCATION.getHeader(), path);
        response(new Response302Status());
    }

    public void responseBody(byte[] body) {
        headers.put(CONTENT_LENGTH.getHeader(), String.valueOf(body.length));
        response(new Response200Status());
    }

    public void badRequest() {
        response(new Response400Status());
    }

    private void response(ResponseStatus responseStatus) {
        try {
            responseStatus.setStatus(dos);
            writeHeader();
            writeBody();
        } catch (IOException e) {
            logger.error(e.getMessage());
            response(new Response500Status());
        }
    }

    private void writeHeader() throws IOException {
        for (String key : headers.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(KEY_VALUE_REGEX);
            sb.append(headers.get(key));
            sb.append(NEW_LINE_PREFIX);
            dos.writeBytes(sb.toString());
        }
        dos.writeBytes(NEW_LINE_PREFIX);
    }

    private void writeBody() throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}

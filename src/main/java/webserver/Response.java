package webserver;

import model.MimeType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final DataOutputStream dos;
    private final Map<String, String> headers = new HashMap<>();

    private Response(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public static Response of(OutputStream dos) {
        return new Response(dos);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String location) throws Exception {
        logger.debug("MIME TYPE : {} {}", MimeType.getMimeType(location), location);

        byte[] body = FileIoUtils.loadFileFromClasspath(location);

        addHeader("Content-Type", MimeType.getMimeType(location).getContentType() + ";charset=utf-8");
        addHeader("Content-Length", Integer.toString(body.length));
        writeResponse200(body);
    }

    public void userListForward(String location, Collection<User> users) throws IOException {
        logger.debug("MIME TYPE : {} {}", MimeType.getMimeType(location), location);

        byte[] body = FileIoUtils.loadCompiledFileFromClassPath(location, users);

        addHeader("Content-Type", MimeType.getMimeType(location).getContentType() + ";charset=utf-8");
        addHeader("Content-Length", Integer.toString(body.length));
        writeResponse200(body);
    }

    private void writeResponse200(byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void sendRedirect(String location) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found\r\n");
        addHeader("Location", location);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.flush();
    }
}

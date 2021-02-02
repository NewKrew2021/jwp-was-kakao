package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class Response200Header implements ResponseHeader{

    private static final Logger logger = LoggerFactory.getLogger(Response200Header.class);

    @Override
    public void responseHeader(DataOutputStream dos, Map<String, String> response) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + response.get("Content-Length") + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

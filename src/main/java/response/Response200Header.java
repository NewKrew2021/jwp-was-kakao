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
            for(String key : response.keySet()){
                StringBuilder sb = new StringBuilder();
                sb.append(key);
                sb.append(KEY_VALUE_REGEX);
                sb.append(response.get(key));
                sb.append(NEW_LINE_PREFIX);
                dos.writeBytes(sb.toString());
            }
            dos.writeBytes(NEW_LINE_PREFIX);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ParseUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String HEADER_REGEX = ":";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Map<String, String> request = new HashMap<>();
            String line = readLine(reader);

            getMethodAndUrl(line,request);

            line = readLine(reader);

            while(!line.isEmpty()){
                getHeaders(line,request);
                line = readLine(reader);
                if(line == null){
                    break;
                }
            }

            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        logger.debug(line);
        return line;
    }

    private void getHeaders(String header, Map<String, String> request) {
        request.put(ParseUtils.parseHeaderKey(header), ParseUtils.parseHeaderValue(header));
    }

    private void getMethodAndUrl(String line, Map<String, String> request){
        String[] lines = line.split(" ");
        request.put("method",lines[0]);
        request.put("url",lines[1]);
        request.put("version",lines[2]);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

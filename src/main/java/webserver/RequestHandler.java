package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.ParseUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String HTTP_GET = "GET";
    private final String HTTP_POST = "POST";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public synchronized void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Map<String, String> request = new HashMap<>();
            String line = readLine(reader);

            getMethodAndUrl(line, request);
            String path = ParseUtils.getUrlPath(request.get("url"));

            if (request.get("method").equals(HTTP_GET)) {
                line = readLine(reader);

                while (!line.isEmpty()) {
                    getHeaders(line, request);
                    line = readLine(reader);
                    if (line == null) {
                        break;
                    }
                }
            }

            if (request.get("method").equals(HTTP_POST)) {
                line = readLine(reader);

                while (!line.isEmpty()) {
                    getHeaders(line, request);
                    line = readLine(reader);
                    if (line == null) {
                        break;
                    }
                }

                String body = IOUtils.readData(reader, Integer.parseInt(request.get("Content-Length")));

                if (path.equals("/user/create")) {
                    Map<String, String> parameters = ParseUtils.getParameters(body);
                    User user = User.mapOf(parameters);
                    logger.debug(user.toString());
                }
            }

            try {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
                logger.error(fsnfe.getMessage());
                response404Header(dos);
            }

        } catch (IOException | URISyntaxException e) {
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

    private void getMethodAndUrl(String line, Map<String, String> request) {
        String[] lines = line.split(" ");
        request.put("method", lines[0]);
        request.put("url", lines[1]);
        request.put("version", lines[2]);
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

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
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

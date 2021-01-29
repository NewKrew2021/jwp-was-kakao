package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Map<String, String> requestParser;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        requestParser = new HashMap<>();
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = bufferedReader.readLine();
            parseFirstLine(line);
            while (!(line = bufferedReader.readLine()).equals("")) {
                if (line == null)
                    return;
                String[] currentLine = line.split(": ");
                requestParser.put(currentLine[0],currentLine[1]);
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            if(FileIoUtils.isExistFile("./templates" + requestParser.get("url"))) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + requestParser.get("url"));
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void parseFirstLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Not a RESTful request");
        }
        String[] currentLine = line.split(" ");

        requestParser.put("method", currentLine[0]);
        requestParser.put("url", currentLine[1]);
        requestParser.put("version", currentLine[2]);
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

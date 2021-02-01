package webserver;

import model.RequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.Parser;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestMessage requestMessage = readRequestMessage(in);
            printRequestMessageHeader(requestMessage.getRequestLine(), requestMessage.getRequestHeader());

            HttpMethod method = Parser.parseMethodFromRequestLine(requestMessage.getRequestLine());
            String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
            url = handlerMapping(method, url, requestMessage.getRequestBody());
            String contentType = Parser.parseContentTypeFromRequestHeader(requestMessage.getRequestHeader());

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(url);
            response200Header(dos, contentType, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestMessage readRequestMessage(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String requestLine = br.readLine();
        Map<String, String> requestHeader = new HashMap<>();

        String line = br.readLine();
        while (line != null && !"".equals(line)) {
            String[] data = line.split(": ");
            requestHeader.put(data[0], data[1]);
            line = br.readLine();
        }

        String requestBody = "";
        if (requestHeader.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(requestHeader.get("Content-Length"));
            requestBody = IOUtils.readData(br, contentLength);
        }

        return RequestMessage.of(requestLine, requestHeader, requestBody);
    }

    private void printRequestMessageHeader(String requestLine, Map<String, String> requestHeader) {
        StringBuilder message = new StringBuilder();
        message.append("\n========= Request Header =========\n")
                .append(requestLine)
                .append("\n");
        for (String key : requestHeader.keySet()) {
            message.append(key)
                    .append(": ")
                    .append(requestHeader.get(key))
                    .append("\n");
        }
        message.append("----------------------------------");
        logger.debug(message.toString());
    }

    private String handlerMapping(HttpMethod method, String url, String body) {
        if (url.contains("/user")) {
            return UserController.handle(method, url, body);
        }
        if (url.contains("/index") || url.contains("/favicon")){
            return "./templates" + url;
        }
        if (url.contains("/css") || url.contains("/fonts") || url.contains("/images") || url.contains("/js")) {
            return "./static" + url;
        }
        return "./templates/error.html";
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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

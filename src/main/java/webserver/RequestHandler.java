package webserver;

import model.RequestMessage;
import model.Response;
import model.ResponseNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import utils.Parser;

import java.io.*;
import java.net.Socket;
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

            Response response = handlerMapping(requestMessage);
            DataOutputStream dos = new DataOutputStream(out);
            response.write(dos);
        } catch (IOException e) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("\n========= Request Header =========\n")
                .append(requestLine)
                .append("\n");
        for (Map.Entry<String, String> entries : requestHeader.entrySet()) {
            sb.append(entries.getKey())
                    .append(": ")
                    .append(entries.getValue())
                    .append("\n");
        }
        sb.append("----------------------------------");
        String message = sb.toString();
        logger.debug(message);
    }

    private Response handlerMapping(RequestMessage requestMessage) {
        String url = Parser.parseURLFromRequestLine(requestMessage.getRequestLine());
        if (ResourceController.isResource(url)) {
            return ResourceController.handle(requestMessage);
        }
        if (url.contains("/user")) {
            return UserController.handle(requestMessage);
        }
        return ResponseNotFound.create();
    }
}

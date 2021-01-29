package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.Parser;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            List<String> requestMessage = getRequestMessage(in);
            Map<String, String> header = Parser.parseHeaderFromRequestMessage(requestMessage);
            printHeader(header);

//            HttpMethod method = Parser.parseMethodFromRequestLine(header.get(0));
//            String url = Parser.parseURLFromRequestLine(header.get(0));
//            String body = Parser.parseBodyFromRequestMessage(requestMessage);
//            handleRequestMapping(method, url, body);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(".templates" + url);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private List<String> getRequestMessage(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        List<String> requestMessage = new ArrayList<>();

        String line = br.readLine();
        while (line != null) {
            requestMessage.add(line);
            line = br.readLine();
        }
        return requestMessage;
    }

    private void printHeader(Map<String, String> header) {
        String message = "\n========= Request Header =========\n";
        for (String key : header.keySet()) {
            message += key + ": " + header.get(key) + "\n";
        }
        message += "----------------------------------";
        logger.debug(message);
    }

    private void handleRequestMapping(HttpMethod method, String url, String body) {
        if (url.contains("/user")) {
            UserController.handleUser(method, url, body);
            return;
        }
        return;
    }

//    private void handleUser(HttpMethod method, String url, List<String> header) {
//        if(method == HttpMethod.GET && url.contains("/create")) {
//            Map<String, String> params = Parser.parseUserParams(header.get(0));
//            User user = User.from(params);
//            logger.debug(user.toString());
//            DataBase.addUser(user);
//        }
//        if(method == HttpMethod.POST && url.contains("/create")) {
////            String body = Parser.parseBody(header);
//            Map<String, String> params = Parser.parseUserParams(header.get(header.size() - 1));
//            User user = User.from(params);
//            logger.debug(user.toString());
//        }
//    }

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

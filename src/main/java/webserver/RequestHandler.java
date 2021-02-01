package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import service.LoginService;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(){}

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            logger.debug("request : {}", line);

            String[] filePath = line.split(" ");

            String[] requestPath = filePath[1].split("\\?");
            // /index.html
            // /user/create
            String path = requestPath[0];
            logger.debug("path : {}", path);
            if (path.equals("/index.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/form.html")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if (path.equals("/user/create")) {
                Map<String, String> strBuf = new HashMap<>();
                while(!(line = br.readLine()).equals("")){
                    String[] buf = line.split(": ");
                    strBuf.put(buf[0], buf[1]);
                    logger.debug("header : {}", line);
                }
                int contentLength = Integer.parseInt(strBuf.get("Content-Length"));
                String body = IOUtils.readData(br,contentLength);
                Map<String, String> argument = parseArgument(body);
                new LoginService().createUser(argument);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            }


        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    protected Map<String, String> parseArgument(String argumentText) throws UnsupportedEncodingException {
        String[] argumentStr = argumentText.split("&");
        Map<String, String> argument = new HashMap<>();

        for (String s : argumentStr) {
            String[] ss = s.split("=");
            argument.put(ss[0], URLDecoder.decode(ss[1], "UTF-8"));
        }
        return argument;
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




    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: http://localhost:8080%s \r\n", url));
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

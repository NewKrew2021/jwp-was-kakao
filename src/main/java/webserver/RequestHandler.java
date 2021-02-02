package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.Response200Header;
import response.Response302Header;
import response.Response404Header;
import response.ResponseHeader;
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
            Map<String, String> responseParameters = new HashMap<>();

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

                String requestBody = IOUtils.readData(reader, Integer.parseInt(request.get("Content-Length")));

                if (path.equals("/user/create")) {
                    Map<String, String> parameters = ParseUtils.getParameters(requestBody);
                    User user = User.mapOf(parameters);
                    DataBase.addUser(user);
                    responseParameters.put("Location", "/index.html");
                    response(dos, new Response302Header(), responseParameters);
                    return;
                }

                if (path.equals("/user/login")) {
                    Map<String, String> parameters = ParseUtils.getParameters(requestBody);
                    User user = DataBase.findUserById(parameters.get("userId"));
                    if (user == null || !user.getPassword().equals(parameters.get("password"))) {
                        responseParameters.put("Location", "/user/login_failed.html");
                        responseParameters.put("Set-Cookie", "logined=false");
                    }
                    else {
                        responseParameters.put("Location", "/index.html");
                        responseParameters.put("Set-Cookie", "logined=true; Path=/");
                    }
                    response(dos, new Response302Header(), responseParameters);
                    return;
                }

            }

            responseParameters.put("path", path);
            response(dos, new Response200Header(), responseParameters);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(DataOutputStream dos, ResponseHeader responseHeader, Map<String, String> responseParameters)
            throws IOException, URISyntaxException {
        try {
            byte[] responseBody = new byte[0];

            if (responseParameters.containsKey("path")) {
                responseBody = FileIoUtils.loadFileFromClasspath("./templates" + responseParameters.get("path"));
            }

            responseParameters.put("Content-Length", String.valueOf(responseBody.length));
            responseHeader.responseHeader(dos, responseParameters);
            responseBody(dos, responseBody);
        } catch (FileSystemNotFoundException | NullPointerException fsnfe) {
            logger.error(fsnfe.getMessage());
            new Response404Header().responseHeader(dos, responseParameters);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

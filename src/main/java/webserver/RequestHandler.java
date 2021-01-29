package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.UserController;
import model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            InputStreamReader reader= new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String str=br.readLine();
            logger.debug("####HTTP Request Header 출력");
            if(str==null) {
                return;
            }
            Request request=new Request(str);
            while (!str.equals("")){
                logger.debug(str);
                str = br.readLine();
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = requestMapper(request);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] filePathToBytes(String path) throws IOException, URISyntaxException {
        String[] paths= path.split("\\.");
        if(paths.length>=2&&(paths[1].equals("html")||paths[1].equals("ico"))){
            return FileIoUtils.loadFileFromClasspath("templates" + path);
        }
        return FileIoUtils.loadFileFromClasspath("static"+ path);
    }

    private byte[] requestMapper(Request request) throws IOException, URISyntaxException {
        if(request.getPaths().length>=1&&request.getPaths()[1].equals("user")){
            return filePathToBytes(new UserController().mapMethod(request));
        }
        return filePathToBytes(request.getPath());
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

package webserver;

import controller.UserController;
import model.Request;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

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
            int contentLength = -1;

            Request request=new Request(str);
            while (!str.equals("")){
                logger.debug(str);
                if(str.contains("Content-Length")){
                    contentLength = Integer.parseInt(str.split(" ")[1]);
                }
                str = br.readLine();
            }
            if(request.getMethodType().equals("POST")){
                request.setParams(IOUtils.readData(br,contentLength));
            }
            DataOutputStream dos = new DataOutputStream(out);
            Response response = requestMapper(request);

            dos.write(response.getHeader().getBytes());
            dos.write(response.getCookie().getBytes());
            dos.write(filePathToBytes(response.getPath()));
            dos.flush();
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

    private Response requestMapper(Request request) throws IOException, URISyntaxException {
        if(request.getPaths().length>=1&&request.getPaths()[1].equals("user")){
            return new UserController().mapMethod(request);
        }
        Response response = new Response();
        response.setPath(request.getPath());
        response.setResponse200Header();
        return response;
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

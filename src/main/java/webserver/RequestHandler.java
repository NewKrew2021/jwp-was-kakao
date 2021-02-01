package webserver;

import HttpRequest.Request;
import annotation.web.RequestMapping;
import annotation.web.RequestMethod;
import controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.*;

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
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            Request request = new Request(br);



            byte[] body = null;
            try {
                File file = new File("src/main/resources/templates" + request.getUri());
                if(file.exists()){
                    body = FileIoUtils.loadFileFromClasspath("./templates" + request.getUri());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                    return;
                }

                file = new File("src/main/resources/static" + request.getUri());
                if(file.exists()){
                    body = FileIoUtils.loadFileFromClasspath("./static" + request.getUri());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200HeaderForCss(dos, body.length);
                    responseBody(dos, body);
                    return;
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            Method[] declaredMethod = RequestController.class.getDeclaredMethods();

            for(Method method: declaredMethod){
                if (method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if(requestMapping.value().equals("/user/create") && requestMapping.method().equals(RequestMethod.GET)){
                        try {
                            Map<String, String> requestParams = request.getParams();
                            method.invoke(new RequestController(), requestParams.get("userId"), requestParams.get("password"), requestParams.get("name"), requestParams.get("email"));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }





//            //body
//            String body = null;
//            if((tempLine = br.readLine()) != null){
//                System.out.println(tempLine);
//                body = tempLine;
//            }





//            String line = br.readLine();
//            if(line == null){
//                return;
//            }
//            System.out.println(line);
//            String tempLine = line;
//            while (!tempLine.equals("")) {
//                tempLine = br.readLine();
//                logger.debug("header : {}", tempLine );
//            }
//
//            String fileLocation = RequestPathSplitUtils.getFileLocation(line);
//
//            if(fileLocation.equals("/user/create")) {
//                String path = line.split(" ")[1];
//                String[] request = path.split("\\?")[1].split("&");
//                Map<String,String> map = new HashMap();
//                for (int i = 0; i < request.length; i++) {
//
//                }
//            }
//
//            byte[] body = null;
//            try {
//                File file = new File("src/main/resources/templates" + fileLocation);
//                if(file.exists()){
//                    body = FileIoUtils.loadFileFromClasspath("./templates" + fileLocation);
//                    DataOutputStream dos = new DataOutputStream(out);
//                    response200Header(dos, body.length);
//                    responseBody(dos, body);
//                }
//                else{
//                    body = FileIoUtils.loadFileFromClasspath("./static" + fileLocation);
//                    DataOutputStream dos = new DataOutputStream(out);
//                    response200HeaderForCss(dos, body.length);
//                    responseBody(dos, body);
//                }
//
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    // GET /index.html HTTP/1.1
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
    // GET /css/style.css HTTP/1.1
    private void response200HeaderForCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
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

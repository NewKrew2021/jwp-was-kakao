package webserver;

import controller.Controller;
import controller.Controllers;
import request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.HttpResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private Controllers controllers = new Controllers();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            Controller controller = controllers.getController(httpRequest.getUri());
            controller.service(httpRequest, httpResponse);

//            Method[] declaredMethod = RequestController.class.getDeclaredMethods();
//            for (Method method : declaredMethod) {
//                if (method.isAnnotationPresent(RequestMapping.class)) {
//                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//                    logger.debug("aa: {}", requestMapping.value());
//                    logger.debug("aa: {}", requestMapping.method());
//                    logger.debug("method Name: {}", method.getName());
//                    logger.debug("method: {}", request.getRequestMethod());
//                    logger.debug("request uri: {}", request.getUri());
//                    logger.debug("request method: {}", request.getRequestMethod());
//
//                    if (requestMapping.value().equals(request.getUri()) && requestMapping.method().equals(request.getRequestMethod())) {
//                        if(requestMapping.value().equals("user/create") && requestMapping.method().equals(RequestMethod.GET)){
//                            try {
//                                Map<String, String> requestParams = request.getParams();
//                                method.invoke(new RequestController(), requestParams.get("userId"), requestParams.get("password"), requestParams.get("name"), requestParams.get("email"));
//                                break;
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        if(requestMapping.value().equals("/user/create") && requestMapping.method().equals(RequestMethod.POST)){
//                            try {
//                                logger.debug("method Name: {}", method.getName());
//                                Map<String, String> requestBody = request.getBody();
//                                method.invoke(new RequestController(), requestBody.get("userId"), requestBody.get("password"), requestBody.get("name"), requestBody.get("email"));
//                                break;
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//            DataOutputStream dos = new DataOutputStream(out);
//            response200Header(dos, body.length);
//            responseBody(dos, body);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}

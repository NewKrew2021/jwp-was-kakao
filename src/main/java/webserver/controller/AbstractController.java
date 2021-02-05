package webserver.controller;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;
import webserver.exceptions.InvalidRequestException;
import webserver.exceptions.NotYetSentException;

public abstract class AbstractController implements Controller {

    public void service(HttpRequest request, HttpResponse response) {
        try {
            System.out.println(request.getMethod().name() + " " + request.getPath());
            methodMapping(request, response);
        } catch (Exception e) {
            if(!response.isSent()){
                System.out.println("자식 컨트롤러에서 처리 누락된 예외 상황 : " + e.getMessage());
                e.printStackTrace();
                response.send(HttpStatusCode.NOT_IMPLEMENTED, e.getMessage());
            }
        } finally {
            if (!response.isSent()) {
                response.send(HttpStatusCode.INTERNAL_SERVER_ERROR);
                throw new NotYetSentException("response의 메서드 send()가 호출되지 않았습니다");
            }
        }
    }

    private void methodMapping(HttpRequest request, HttpResponse response) {
        switch (request.getMethod()) {
            case GET:
                validateGetRequest(request, response);
                doGet(request, response);
                return;
            case POST:
                validatePostRequest(request, response);
                doPost(request, response);
                return;
            case PUT:
                validatePutRequest(request, response);
                doPut(request, response);
                return;
            case DELETE:
                validateDeleteRequest(request, response);
                doDelete(request, response);
                return;
            default:
                response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doGet(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doPut(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public void doDelete(HttpRequest request, HttpResponse response) {
        response.send(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    // 필요시에 오버라이딩 하여 인풋값을 검증한다
    public void validatePostRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
    }

    public void validateGetRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
    }

    public void validatePutRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
    }

    public void validateDeleteRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
    }
}

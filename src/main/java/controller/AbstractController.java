package controller;

import dto.HttpRequest;
import dto.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException{
        try {
            if (request.getMethod().equals("GET")) {
                doGet(request, response);
            }

            if (request.getMethod().equals("POST")) {
                doPost(request, response);
            }
        }catch(UnsupportedEncodingException e){
            throw new IOException("디코딩 중에 문제가 발생하였습니다.");
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {}

    public void doGet(HttpRequest request, HttpResponse response){}
}

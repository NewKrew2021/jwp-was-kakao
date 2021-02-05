package controller;

import annotation.web.RequestMethod;
import model.Request;
import model.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response){
        if(request.getMethod().equals(RequestMethod.GET)){
            doGet(request,response);
        }
        if(request.getMethod().equals(RequestMethod.POST)){
            doPost(request,response);
        }
    }

    abstract void doPost(Request request, Response response);

    abstract void doGet(Request request, Response response);

}

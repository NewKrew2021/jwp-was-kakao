package controller;

import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) throws Exception {

    }

    public void doPost(Request request, Response response)  throws Exception {

    }


    public void doGet(Request request, Response response) throws Exception {

    }
}

package controller;

import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller{
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

}

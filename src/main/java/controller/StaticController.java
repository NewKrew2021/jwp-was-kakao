package controller;

import request.HttpRequest;
import response.HttpResponse;
import utils.HttpResponseUtils;

public class StaticController extends AbstractController {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        httpResponse.setContentType(HttpResponseUtils.findContentType(path));
        httpResponse.forwardBody(httpResponse.responseBody(path));
    }

}

package controller;

import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ForwardController extends AbstractController{
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {

        if(FileIoUtils.exist("./templates" + httpRequest.getPath())){
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            httpResponse.forward(body);
        }
        if(FileIoUtils.exist("./static" + httpRequest.getPath())){
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath());
            httpResponse.forward(body);
        }

    }
}

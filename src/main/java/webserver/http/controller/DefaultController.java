package webserver.http.controller;

import com.google.common.base.Charsets;
import utils.ClasspathResourceLoader;
import utils.ResourceLoader;
import webserver.http.*;
import webserver.http.utils.FileExtentions;

public class DefaultController implements Controller {

    private ResourceLoader DEFAULT_RESOURCE_LOADER = new ClasspathResourceLoader("./templates");
    private ResourceLoader resourceLoader;

    public DefaultController() {
        resourceLoader = DEFAULT_RESOURCE_LOADER;
    }

    public DefaultController(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        String content = resourceLoader.load(httpRequest.getPath());
        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(
                MimeType.fromExtenstion(FileExtentions.fromPath(httpRequest.getPath())),
                Charsets.UTF_8);
        httpResponse.setBody(content.getBytes());
    }

}

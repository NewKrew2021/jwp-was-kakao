package webserver.http.controller;

import utils.ClasspathResourceLoader;
import utils.ResourceLoader;
import webserver.http.*;

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
        ContentType contentType = findContentType(httpRequest.getPath());
        if( contentType != ContentType.NOTHING )
            httpResponse.setContentType(findContentType(httpRequest.getPath()).toString());
        httpResponse.setBody(content.getBytes());
    }

    private ContentType findContentType(String path) {
        FileExtension extension = FileExtension.fromPath(path);
        return ContentType.valueOf(extension);
    }


}

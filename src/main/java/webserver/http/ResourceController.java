package webserver.http;

public class ResourceController implements Controller {

    private ResourceLoader resourceLoader;

    public ResourceController(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ModelAndView execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        return ModelAndView.of(new StaticResourceView(resourceLoader, httpRequest.getPath()));
    }

}

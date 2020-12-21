package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.controller.MainController;
import webserver.controller.StaticResourceController;
import webserver.controller.UserController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.util.StaticResourceUtils;

public class RequestMappingHandler {

    private static final String USER_CONTROLLER_MAPPING_PATH = "/user";

    public RequestMappingHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void resolve(HttpRequest request, HttpResponse httpResponse) throws Exception {
        if(StringUtils.countMatches(request.getPath(), "/") == 1){
            new MainController().service(request, httpResponse);
        }else if(request.getPath().startsWith(USER_CONTROLLER_MAPPING_PATH)){
            new UserController().service(request, httpResponse);
        }else if(StaticResourceUtils.isStaticResourcePath(request.getPath())){
            new StaticResourceController().service(request, httpResponse);
        }else{
            new StaticResourceController().service(request, httpResponse);
        }
    }

}

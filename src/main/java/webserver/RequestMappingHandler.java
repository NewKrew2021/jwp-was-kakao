package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.controller.HttpController;
import webserver.controller.MainController;
import webserver.controller.StaticResourceController;
import webserver.controller.UserController;
import webserver.http.HttpRequest;
import webserver.util.StaticResourceUtils;

public class RequestMappingHandler {

    private static final String USER_CONTROLLER_MAPPING_PATH = "/user";

    public RequestMappingHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static HttpController getController(HttpRequest request){
        if(StringUtils.countMatches(request.getPath(), "/") == 1){
            return new MainController();
        }else if(request.getPath().startsWith(USER_CONTROLLER_MAPPING_PATH)){
            return new UserController();
        }else if(StaticResourceUtils.isStaticResourcePath(request.getPath())){
            return new StaticResourceController();
        }else{
            return new StaticResourceController();
        }
    }

}

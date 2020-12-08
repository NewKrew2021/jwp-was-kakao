package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import webserver.controller.HttpController;
import webserver.controller.MainController;
import webserver.controller.StaticResourceController;
import webserver.controller.UserController;
import webserver.http.HttpRequest;

import java.util.Arrays;

public class RequestMappingHandler {

    private static final String[] STATIC_RESOURCE_PATH_SUFFIX = {".html", ".css", ".js", ".eot", ".svg", ".ttf", ".woff", ".woff2", ".png"};
    private static final String USER_CONTROLLER_MAPPING_PATH = "/user";
    public static final String TEMPLATES_RESOURCE_PATH_PREFIX = "./templates";
    public static final String STATIC_RESOURCE_PATH_PREFIX = "./static";


    public static HttpController getController(HttpRequest request){
        if(StringUtils.countMatches(request.getPath(), "/") == 1){
            return new MainController();
        }else if(request.getPath().startsWith(USER_CONTROLLER_MAPPING_PATH)){
            return new UserController();
        }else if(Arrays.asList(STATIC_RESOURCE_PATH_SUFFIX).contains(request.getPath())){
            return new StaticResourceController();
        }else{
            return new StaticResourceController();
        }
    }

}

package webserver.util;

import webserver.config.ServerConfigConstants;

public class StaticResourceUtils {
    public static boolean isStaticResourcePath(String requestPath){
        for(String suffix : ServerConfigConstants.STATIC_RESOURCE_PATH_SUFFIX){
            if(requestPath.indexOf(suffix) > -1){
                return true;
            }
        }
        return false;
    }
}

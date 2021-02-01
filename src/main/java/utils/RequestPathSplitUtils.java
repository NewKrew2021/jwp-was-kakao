package utils;

import annotation.web.RequestMethod;
import HttpRequest.RequestUri;

import java.util.HashMap;
import java.util.Map;

public class RequestPathSplitUtils {
    public static String getFileLocation(String request){
        String splitStrings = request.split(" ")[1];
        String[] tempCreateUserRequest = splitStrings.split("\\?");
        if(tempCreateUserRequest[0].equals("/user/create")) {
            return tempCreateUserRequest[0];
        }
        return  request.split(" ")[1];
    }


    public static RequestUri getRequestURI(String line){
        String[] splitLine = line.split(" ");
        return new RequestUri(getMethodType(splitLine[0]), extractPath(splitLine[1]), extractParams(splitLine[1]));
    }


    public static RequestMethod getMethodType(String line){
        String method = line.split(" ")[0];
        if(method.equals("GET")){
            return RequestMethod.GET;
        }
        if (method.equals("POST")){
            return RequestMethod.POST;
        }
        if (method.equals("DELETE")){
            return RequestMethod.DELETE;
        }
        if(method.equals("PUT")){
            return RequestMethod.PUT;
        }
        if(method.equals("PATCH")){
            return RequestMethod.PATCH;
        }
        throw new RuntimeException();
    }

    public static String extractPath(String uri){
        return uri.split("\\?")[0];
    }

    public static Map<String, String> extractParams(String uri){
        String[] tmp =  uri.split("\\?");
        if(tmp.length <= 1){
            return null;
        }

        Map<String, String> result = new HashMap<>();
        String[] splitString = tmp[1].split("&");
        for(String param: splitString){
            String[] splitParam = param.split("=");
            result.put(splitParam[0], splitParam[1]);
        }
        return result;
    }
}

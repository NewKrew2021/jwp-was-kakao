package annotation.web;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod stringToRequestMethod(String method){
        if(method.equals("GET")){
            return RequestMethod.GET;
        }
        if (method.equals("POST")){
            return RequestMethod.POST;
        }
        if (method.equals("PUT")){
            return RequestMethod.PUT;
        }
        if (method.equals("DELETE")){
            return RequestMethod.DELETE;
        }
        if(method.equals("HEAD")){
            return RequestMethod.HEAD;
        }
        if (method.equals("PATCH")){
            return RequestMethod.PATCH;
        }
        if (method.equals("OPTIONS")){
            return RequestMethod.OPTIONS;
        }
        if (method.equals("TRACE")){
            return RequestMethod.TRACE;
        }
        throw new IllegalArgumentException();
    }
}

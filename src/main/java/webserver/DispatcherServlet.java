package webserver;

import dto.HttpRequest;
import dto.HttpResponse;

public class DispatcherServlet {

    public static HttpResponse run(HttpRequest request){
        if(request.getMethod().equals("POST")){
            if(request.getUri().startsWith("/user/create")){
                return UserController.create(request);
            }
        }

        if(request.getMethod().equals("GET")){
            if(request.getUri().endsWith(".html")){
                return FileController.get(request);
            }
        }

        return new HttpResponse("HTTP/1.1 404 NOT FOUND");
   }
}

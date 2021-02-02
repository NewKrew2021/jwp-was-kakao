package webserver;

import dto.HttpRequest;
import dto.HttpResponse;

public class DispatcherServlet {

    public static HttpResponse run(HttpRequest request){
        if(request.getMethod().equals("POST")){
            if(request.getUri().startsWith("/user/create")){
                return UserController.create(request);
            }
            if(request.getUri().startsWith("/user/login")){
                return UserController.login(request);
            }
        }

        if(request.getMethod().equals("GET")){
            if(request.getUri().endsWith(".html") || request.getUri().endsWith(".css") || request.getUri().endsWith(".js")){
                return FileController.get(request);
            }

            if(request.getUri().startsWith("/user/list")){
                try{
                    return UserController.getUsers(request);
                } catch(Exception e){

                }
            }
        }

        return new HttpResponse("HTTP/1.1 404 NOT FOUND");
   }
}

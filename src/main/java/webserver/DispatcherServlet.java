package webserver;

import dto.HttpRequest;

public class DispatcherServlet {

    public static byte[] run(HttpRequest request){
        if(request.getMethod().equals("POST")){
            // /user 이면
            if(request.getUri().startsWith("/user/create")){
                return UserController.create(request);
            }
        }

        if(request.getMethod().equals("GET")){
            // 파일 요청이면
            if(request.getUri().endsWith(".html")){
                return FileController.get(request);
            }
        }
        return new byte[0];
   }
}

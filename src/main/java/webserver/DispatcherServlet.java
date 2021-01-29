package webserver;

import dto.HttpRequest;

public class DispatcherServlet {

    public static byte[] run(HttpRequest request){
        if(request.getMethod().equals("GET")){
            // /user 이면
            if(request.getUri().startsWith("/user/create")){
                return UserController.create(request);
            }
            // 파일 요청이면
            if(request.getUri().endsWith(".html")){
                return FileController.get(request);
            }
        }
        return new byte[0];
   }
}

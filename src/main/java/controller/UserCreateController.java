package controller;

import model.User;
import service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseBuilder;
import webserver.http.ParameterValidator;

public class UserCreateController extends Controller {
    private static final String PATH = "/user/create";

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        addNewUser(httpRequest);
        return HttpResponse._200_OK;
    }

    @Override
    protected HttpResponse handlePost(HttpRequest httpRequest) {
        addNewUser(httpRequest);
        return new HttpResponseBuilder()
                .with302Redirect("/index.html")
                .build();
    }

    private void addNewUser(HttpRequest request) {
        ParameterValidator.validate(request, "userId", "password", "name", "email");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        User user = new User(userId, password, name, email);
        UserService.addNewUser(user);
    }
}

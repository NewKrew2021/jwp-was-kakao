package webserver;

import db.DataBase;
import model.User;

public class CreateUserController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) {
        User user = User.createUser(httpRequest.getEntity());
        DataBase.addUser(user);
        Response response = new Response();
        response.setHeaders("Location: /index.html");
        return response;
    }
}

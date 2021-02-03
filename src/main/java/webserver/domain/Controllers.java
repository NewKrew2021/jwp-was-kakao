package webserver.domain;

import com.google.common.collect.Maps;
import user.controller.CreateUserController;
import user.controller.ListUserController;
import user.controller.LoginController;
import user.controller.UserProfileController;
import webserver.controller.Controller;
import webserver.controller.ForwardController;

import java.util.Map;

public class Controllers {
    private final Map<String, Controller> controllers = Maps.newHashMap();

    public Controllers() {
        controllers.put("/user/profile", new UserProfileController());
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("*", new ForwardController());
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        controllers.getOrDefault(processedPath(httpRequest.getPath()), controllers.get("*"))
                .service(httpRequest, httpResponse);
    }

    private String processedPath(String path) {
        if (path.contains(".")) {
            return "*";
        }

        return path;
    }
}

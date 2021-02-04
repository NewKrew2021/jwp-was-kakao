package webserver.domain;

import com.google.common.collect.Maps;
import user.ui.CreateUserController;
import user.ui.ListUserController;
import user.ui.LoginController;
import user.ui.UserProfileController;
import webserver.ui.*;

import java.util.Map;

public class Controllers {
    private static final String USER_PROFILE_PATH = "/user/profile";
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LIST_PATH = "/user/list";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String DEFAULT = "*";
    private static final String DOT = ".";
    private final Map<String, Controller> controllers = Maps.newHashMap();

    public Controllers() {
        controllers.put(USER_PROFILE_PATH, new UserProfileController());
        controllers.put(USER_CREATE_PATH, new CreateUserController());
        controllers.put(USER_LIST_PATH, new ListUserController());
        controllers.put(USER_LOGIN_PATH, new LoginController());
        controllers.put(DEFAULT, new ForwardController());
    }

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        controllers.getOrDefault(processedPath(httpRequest.getPath()), controllers.get(DEFAULT))
                .service(httpRequest, httpResponse);
    }

    private String processedPath(String path) {
        if (path.contains(DOT)) {
            return DEFAULT;
        }

        return path;
    }
}

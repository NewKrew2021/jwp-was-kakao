package webserver;

import dto.ParamValue;
import service.UserService;

import java.util.Optional;

public class URIFactory {

    private UserService userService = new UserService();

    public void create(String path) {
        String uri = ParseURI.getURI(path);
        Optional<String> params = ParseURI.getParams(path);

        if (uri.startsWith("/user/create")) {
            ParamValue paramVale = ParamValue.of(params);
            userService.create(paramVale);
        }
    }
}

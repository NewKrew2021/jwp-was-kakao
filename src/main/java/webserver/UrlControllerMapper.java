package webserver;

import controller.*;

import java.util.Arrays;

public enum UrlControllerMapper {
    CREATE_USER_CONTROLLER("/user/create", CreateUserController.getInstance()),
    LIST_USER_CONTROLLER("/user/list", ListUserController.getInstance()),
    LOGIN_CONTROLLER("/user/login", LoginController.getInstance()),
    FILE_CONTROLLER("", FileController.getInstance()),
    NOT_FOUND("", null);

    private final String url;
    private final Controller controller;

    UrlControllerMapper(String url, Controller controller) {
        this.url = url;
        this.controller = controller;
    }

    public static UrlControllerMapper of(String url) {
        return Arrays.stream(UrlControllerMapper.values())
                .filter(controller -> controller.isSameUrl(url))
                .findAny()
                .orElse(NOT_FOUND);
    }

    private boolean isSameUrl(String url) {
        return this.url.equals(url) ||
                (this == FILE_CONTROLLER && url.matches(".*\\..+"));
    }

    public boolean isNotFound() {
        return this == NOT_FOUND;
    }

    public Controller getController() {
        return controller;
    }
}

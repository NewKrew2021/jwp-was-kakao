package model;

import controller.*;
import exception.NotExistPathException;
import org.checkerframework.checker.index.qual.HasSubsequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PageUrl {

    USER_CREATE_PAGE("/user/create"),
    LOGIN_PAGE("/user/login"),
    USER_LIST_PAGE("/user/list.html"),
    NOT_PAGE("");

    private String url;
    private static Map<PageUrl, Controller> controllers;

    PageUrl(String url) {
        this.url = url;
    }

    static {
        controllers = new HashMap<>();
        controllers.put( USER_CREATE_PAGE, new CreateUserController() );
        controllers.put( LOGIN_PAGE, new LoginController());
        controllers.put( USER_LIST_PAGE, new ListUserController() );
        controllers.put( NOT_PAGE, new FowardController());
    }

    public static PageUrl of(String url) {
        return Arrays.stream(values())
                .filter(v -> url.equals(v.url))
                .findFirst()
                .orElse(NOT_PAGE);
    }

    public Controller getController() {
        return controllers.get(this);
    }

    public String getPageUrl() {
        return url;
    }

}

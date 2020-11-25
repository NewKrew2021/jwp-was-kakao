package webserver.http.controller;

import webserver.http.HttpRequestParam;

import java.util.List;

public interface RequestDataObject {

    default String getValue(List<HttpRequestParam> params, String paramName) {
        return params.stream()
                .filter(it -> paramName.equals(it.getName()))
                .findFirst()
                .get()
                .getValue();
    }

}

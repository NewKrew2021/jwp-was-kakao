package webserver.http.controller;

import webserver.http.HttpRequestParam;

import java.util.List;

public interface RequestDataObjectValidator {
    void validate(List<HttpRequestParam> params);
}

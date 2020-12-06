package apps.slipp.controller;

import webserver.http.HttpRequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class THttpRequestParams {

    public static List<HttpRequestParam> params(String... params){
        return Arrays.stream(params).map(HttpRequestParam::new).collect(Collectors.toList());
    }
}

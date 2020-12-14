package webserver.http.parser;

import webserver.http.ParameterBag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ParameterBagParser {

    public static ParameterBag parse(String queryString) {

        if (queryString == null) return new ParameterBag(new HashMap<String, String>());

        String[] querys = queryString.split("&");

        Map<String, String> map = Arrays.stream(querys)
                .map(query -> query.split("="))
                .collect(toMap(v -> v[0], v -> v.length > 1 ? v[1] : ""));

        return new ParameterBag(map);
    }
}
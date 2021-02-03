package utils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static Map<String, String> getParamMap(String params) {
        Map<String, String> paramMap = new HashMap<>();
        for(String param : params.split("&")) {
            paramMap.put(param.split("=")[0], param.split("=")[1]);
        }
        return paramMap;
    }
}

package http;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpParameters {

    private Map<String, String> parameters = new HashMap<>();

    public void parseParameter(String param) {
        String[] parameters = param.split("&|=");
        for (int i = 0; i < parameters.length; i = i + 2) {
            this.parameters.put(parameters[i], URLDecoder.decode( parameters[i + 1], StandardCharsets.UTF_8 ));
        }
    }

    public String getParameterByKey(String key) {
        return parameters.getOrDefault( key , "");
    }

}

package utils;

import java.util.HashMap;
import java.util.Map;

public class QueryStringParserUtils {
    public static Map<String, String> getParameterMapFromText(String argumentText, String delimiter) {
        String[] arguments = argumentText.split(delimiter);
        Map<String, String> argumentMap = new HashMap<>();
        for (String argument : arguments) {
            String[] parameter = argument.split("=");
            argumentMap.put(parameter[0], parameter[1].trim());
        }
        return argumentMap;
    }
}

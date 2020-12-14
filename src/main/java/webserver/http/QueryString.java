package webserver.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class QueryString {

    private static final String PARAMETER_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final HashMap<String, String> param = new HashMap<>();

    public QueryString(String value) throws UnsupportedEncodingException {
        String[] values = value.split(PARAMETER_DELIMITER);
        for (String s : values) {
            String[] query = s.split(VALUE_DELIMITER);
            addParameter(query);
        }
    }

    private void addParameter(String[] query) throws UnsupportedEncodingException {
        if(query.length > 1) {
            param.put(query[KEY_INDEX], URLDecoder.decode(query[VALUE_INDEX], "UTF-8"));
        }
    }

    public String getParameter(String key) {
        return param.get(key);
    }
}

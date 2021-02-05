package webserver.domain;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Headers {
    private static final String COLON = ":";
    private Map<String, String> headers;

    public Headers() {
        headers = Maps.newHashMap();
    }

    public Headers(BufferedReader br) {
        this.headers = Maps.newHashMap();
        makeHeaders(br);
    }

    private void makeHeaders(BufferedReader br) {
        for (String line = getNextLine(br); !"".equals(line) && line != null; line = getNextLine(br)) {
            String[] tokens = line.split(COLON);
            headers.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    private String getNextLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String get(String key, String defaultValue) {
        return headers.getOrDefault(key, defaultValue);
    }

    public Set<String> keySet() {
        return headers.keySet();
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }
}

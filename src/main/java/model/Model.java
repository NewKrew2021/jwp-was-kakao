package model;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Object> data;

    private Model(Map<String, Object> data) {
        this.data = data;
    }

    public static Model empty() {
        return new Model(new HashMap<>());
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public Map<String, Object> getData() {
        return data;
    }
}

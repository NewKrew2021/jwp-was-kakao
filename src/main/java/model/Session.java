package model;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private String id;
    private Map<String, Object> value;

    public Session(String id, Map<String, Object> value){
        this.id = id;
        this.value = value;
    }

    public static Session of(String id){
        return new Session(id, new HashMap<>());
    }

    public String getId(){
        return id;
    }

    public void setAttribute(String name, Object sessionValue){
        value.put(name, sessionValue);
    }

    public Object getAttribute(String name){
        return value.get(name);
    }

    public void removeAttribute(String name){
        value.remove(name);
    }

    public void invalidate(){
        value.clear();
    }
}

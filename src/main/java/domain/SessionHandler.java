package domain;

import model.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionHandler {
    private static Map<String, Session> sessions = new HashMap<>();

    public static void addSession(String id){
        sessions.put(id, Session.of(id));
    }

    public static boolean getSession(Optional<String> id){
        return sessions.containsKey(id) ;
    }
}

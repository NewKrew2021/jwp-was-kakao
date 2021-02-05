package session;

import com.google.common.collect.Maps;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sessions {
    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    public static String add(User user){
        String sessionId = String.valueOf(UUID.randomUUID());
        HttpSession httpSession = new HttpSession(sessionId);
        httpSession.setAttribute("userId", user.getUserId());
        httpSession.setAttribute("password", user.getPassword());
        httpSession.setAttribute("name", user.getName());
        httpSession.setAttribute("email", user.getEmail());
        sessions.put(sessionId, httpSession);
        return sessionId;
    }

    public static void remove(String sessionId){
        sessions.remove(sessionId);
    }

    public static boolean isContain(String sessionId){
        return sessions.containsKey(sessionId);
    }

}

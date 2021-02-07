package request;

import java.util.Map;
import java.util.Optional;

public class HttpSessions {

    private static Map<String, HttpSession> httpSessions;

    public HttpSessions(Map<String, HttpSession> httpSessions) {
        this.httpSessions = httpSessions;
    }

    public Map<String, HttpSession> getHttpSessions() {
        return httpSessions;
    }

    public void clear() {
        httpSessions.clear();
    }

    public static HttpSession getNewHttpSession(String email) {
        HttpSession httpSession = HttpSession.of();
        httpSession.setAttribute("email",email);
        httpSessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public boolean containsKey(String key) {
        return httpSessions.containsKey(key);
    }

    public static Optional<HttpSession> findHttpSessionById(String id){
        return Optional.ofNullable(httpSessions.get(id));
    }

    public static boolean existHttpSession(String sessionId){
        return httpSessions.containsKey(sessionId);
    }
}

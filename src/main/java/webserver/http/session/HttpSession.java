package webserver.http.session;

public interface HttpSession {

    SessionId getId();

    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    void removeAttribute(String name);

    void invalidate();

}

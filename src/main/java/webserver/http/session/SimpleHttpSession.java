package webserver.http.session;

import java.util.UUID;

public class SimpleHttpSession implements HttpSession{

    private final SessionId id;

    SimpleHttpSession(){
        this.id = SessionId.of(UUID.randomUUID().toString());
    }

    @Override
    public SessionId getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public void invalidate() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleHttpSession that = (SimpleHttpSession) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

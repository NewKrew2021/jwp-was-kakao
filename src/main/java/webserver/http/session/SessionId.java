package webserver.http.session;

import java.util.Objects;

public interface SessionId {

    String getId();

    static SessionId of(String id) {
        return new SessionId() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public String toString() {
                return id;
            }

            @Override
            public int hashCode() {
                return Objects.hash(id);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                SessionId that = (SessionId) o;
                return id.equals(that.getId());
            }
        };
    }
}

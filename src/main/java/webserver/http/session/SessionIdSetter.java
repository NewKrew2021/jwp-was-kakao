package webserver.http.session;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpResponsePreProcessor;
import webserver.http.MimeType;
import webserver.http.utils.FileExtentions;

import java.text.MessageFormat;

public class SessionIdSetter implements HttpResponsePreProcessor {

    private final HttpSessionManager sessionManager;
    private int expiredHours = 1;

    public SessionIdSetter(HttpSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean matches(HttpRequest request, HttpResponse response) {
        // agent 를 보고 set-cookie 로 session id 를 구워줄지 결정하는게 좋겠지만 일단 mimetype 으로 땜빵 해봅니다. -_-a
        MimeType mimeType = MimeType.fromExtenstion(FileExtentions.fromPath(request.getPath()));
        if( mimeType == MimeType.TEXT_HTML && request.getSession(false) == null ) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(HttpResponse response) {
        HttpSession session = sessionManager.createSession();
        response.setSetCookie(MessageFormat.format("{0}={1}; Path=/", HttpSessions.SESSION_ID_COOKIE_HEADER, session.getId() ));
    }
}

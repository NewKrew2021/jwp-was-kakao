package webserver.http;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ServerErrorHandler implements ExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ServerErrorHandler.class);

    @Override
    public void handle(Exception e, HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug(e.getMessage(), e);
        httpResponse.setStatus(HttpStatus.x500_InternalServerError);
        httpResponse.setBody(e.getMessage().getBytes(Charsets.UTF_8));
        httpResponse.send();
    }
}

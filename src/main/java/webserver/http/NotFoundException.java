package webserver.http;

import java.text.MessageFormat;

public class NotFoundException extends HttpStatusCodeException {

    public NotFoundException(String path) {
        super(MessageFormat.format("{0} 를 찾을 수 없습니다.", path));
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.x404_NotFound;
    }
}

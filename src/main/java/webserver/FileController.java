package webserver;

import utils.FileIoUtils;
import webserver.request.ContentType;
import webserver.request.RequestPath;
import webserver.response.HttpResponse;

public class FileController {
    public HttpResponse getFileResponse(RequestPath path) {
        if (path.requiresCss()) {
            return getFileResponse(path, ContentType.CSS);
        }
        return getFileResponse(path, ContentType.HTML);
    }

    private HttpResponse getFileResponse(RequestPath path, ContentType contentType) {
        try {
            return HttpResponse.file(FileIoUtils.loadFileFromClasspath(path.asFilePath()), contentType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

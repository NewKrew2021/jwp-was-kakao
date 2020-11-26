package webserver;

import utils.FileIoUtils;
import webserver.request.ContentType;
import webserver.request.RequestPath;
import webserver.response.Response;

public class FileController {
    public Response getFileResponse(RequestPath path) {
        if (path.requiresCss()) {
            return getFileResponse(path, ContentType.CSS);
        }
        return getFileResponse(path, ContentType.HTML);
    }

    private Response getFileResponse(RequestPath path, ContentType contentType) {
        try {
            return Response.file(FileIoUtils.loadFileFromClasspath(path.asFilePath()), contentType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

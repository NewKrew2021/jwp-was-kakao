package webserver;

import webserver.request.Request;
import webserver.request.RequestPath;
import webserver.response.Response;

public class Router {
    private static final String USER_PATH = "/user";

    private final UserController userController;
    private final FileController fileController;

    public Router() {
        userController = new UserController();
        fileController = new FileController();
    }

    public Response getResponse(Request request) {
        RequestPath path = request.getHeader().getPath();
        if (path.requiresFile()) {
            return fileController.getFileResponse(path);
        }
        if (path.startsWith(USER_PATH)) {
            return userController.getResponse(request);
        }
        return Response.notFound();
    }
}

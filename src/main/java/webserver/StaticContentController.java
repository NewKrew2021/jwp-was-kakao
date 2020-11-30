package webserver;

import utils.FileIoUtils;

import static webserver.RequestHandler.getBasePath;

class StaticContentController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) throws Exception {
        String requestURI = httpRequest.getRequestURI();
        byte[] body = FileIoUtils.loadFileFromClasspath(getBasePath(requestURI) + requestURI);
        Response response = new Response();
        response.setBody(body);
        response.setHeaders("Content-Type: " + MimeType.fromFileName(requestURI).getMimeTypeValue());
        return response;
    }
}

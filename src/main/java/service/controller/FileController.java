package service.controller;

import framework.request.HttpRequest;
import framework.response.HttpResponse;
import utils.FileIoUtils;
import utils.ParseUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static framework.common.HttpHeaders.CONTENT_LENGTH;
import static framework.common.HttpHeaders.CONTENT_TYPE;

public class FileController extends AbstractController {

    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        try {
            byte[] body = readFromPath(request.getPath());
            response.addHeader(CONTENT_TYPE.getHeader(), getContentType(request.getPath()));
            response.addHeader(CONTENT_LENGTH.getHeader(), String.valueOf(body.length));
            response.responseBody(body);
        } catch (URISyntaxException e) {
            response.badRequest();
        } catch (NullPointerException e) {
            response.notFound();
        }
    }

    private byte[] readFromPath(String path) throws IOException, URISyntaxException, NullPointerException {
        return FileIoUtils.loadFileFromClasspath(attachBasePath(path));
    }

    private String getContentType(String path) {
        return FileExtension.getFileExtensionToExtension(ParseUtils.getExtension(path)).getContentType();
    }

    private String attachBasePath(String path) {
        FileExtension fileExtension = FileExtension.getFileExtensionToExtension(ParseUtils.getExtension(path));
        if(fileExtension.getResourceType().equals(FileExtension.ResourceType.STATIC)) {
            return STATIC_PATH + path;
        }
        return TEMPLATE_PATH + path;
    }

}

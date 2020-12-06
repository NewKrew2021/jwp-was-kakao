package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import exception.InvalidResourceException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements Controller {

	@Override
	public void execute(HttpRequest request, HttpResponse response) {
		try {
			String resourcePath = RequestPathUtils.getResourcePath(request.getPath());
			response.setHttpStatus(HttpStatus.OK);
			request.setContentType(request.getContentType());
			response.forwardBody(FileIoUtils.loadFileFromClasspath(resourcePath));
		} catch (IOException | URISyntaxException e) {
			throw new InvalidResourceException(e.getMessage());
		}
	}
}

package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import domain.HttpStatus;
import exception.InvalidResourceException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController extends AbstractController {

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		try {
			String resourcePath = RequestPathUtils.getResourcePath(request.getPath());
			response.forwardBody(FileIoUtils.loadFileFromClasspath(resourcePath));
		} catch (IOException | URISyntaxException e) {
			throw new InvalidResourceException(e.getMessage());
		}
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		// Nothing
	}

	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		response.setHttpStatus(HttpStatus.OK);
		request.setContentType(request.getContentType());
		service(request, response);
	}
}

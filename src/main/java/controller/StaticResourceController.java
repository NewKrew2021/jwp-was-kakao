package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.InvalidResourceException;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements Controller {

	@Override
	public void execute(HttpRequest request, HttpResponse response) {
		if ("/".equals(request.getPath()))
			response.forward();

		try {
			String resourcePath = RequestPathUtils.getResourcePath(request.getPath());
			response.forwardBody(FileIoUtils.loadFileFromClasspath(resourcePath));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			throw new InvalidResourceException(e.getMessage());
		}
	}
}

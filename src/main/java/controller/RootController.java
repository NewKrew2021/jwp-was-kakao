package controller;

import domain.HttpRequest;
import domain.HttpResponse;

public class RootController implements Controller{
	@Override
	public void execute(HttpRequest request, HttpResponse response) {
		response.setContentType(request.getContentType());
		response.forward(request.getPath());
	}
}

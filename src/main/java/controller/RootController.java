package controller;

import domain.HttpRequest;
import domain.HttpResponse;

public class RootController extends AbstractController {
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		response.forward(request.getPath());
	}

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		// Nothing
	}

	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
		service(request, response);
	}
}

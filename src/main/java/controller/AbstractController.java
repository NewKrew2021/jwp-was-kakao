package controller;

import domain.HttpRequest;
import domain.HttpResponse;

public abstract class AbstractController implements Controller {
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		response.setContentType(request.getContentType());
	}

	public abstract void doPost(HttpRequest request, HttpResponse response);
	public abstract void doGet(HttpRequest request, HttpResponse response);
}

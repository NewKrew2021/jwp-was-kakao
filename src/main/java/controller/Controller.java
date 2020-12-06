package controller;

import domain.HttpRequest;
import domain.HttpResponse;

public interface Controller {
	void execute(HttpRequest request, HttpResponse response);
}

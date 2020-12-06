package controller;

import domain.HttpMethod;

public class HttpRouter {
	private AbstractController controller;
	private HttpMethod method;
	private String path;

	public HttpRouter(String path, HttpMethod method, AbstractController controller) {
		this.path = path;
		this.method = method;
		this.controller = controller;
	}

	public AbstractController getController() {
		return controller;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}
}

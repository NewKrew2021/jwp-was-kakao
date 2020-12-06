package webserver;

import controller.*;
import domain.HttpMethod;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ControllerRouter {
	private static final HttpRouter ROOT_ROUTE = new HttpRouter("/", HttpMethod.GET, new RootController());
	private static final List<HttpRouter> routers = new ArrayList<>();

	static {
		routers.add(new HttpRouter("/user/create", HttpMethod.POST, new JoinController()));
		routers.add(new HttpRouter("/user/login", HttpMethod.POST, new LoginController()));
		routers.add(new HttpRouter("/user/list", HttpMethod.GET, new MembersController()));
		routers.add(new HttpRouter("/images/", HttpMethod.GET, new StaticResourceController()));
		routers.add(new HttpRouter("/fonts/", HttpMethod.GET, new StaticResourceController()));
		routers.add(new HttpRouter("/js/", HttpMethod.GET, new StaticResourceController()));
		routers.add(new HttpRouter("/css/", HttpMethod.GET, new StaticResourceController()));
		routers.add(new HttpRouter(".html", HttpMethod.GET, new StaticResourceController()));
		routers.add(new HttpRouter(".ico", HttpMethod.GET, new StaticResourceController()));
	}

	private HttpRequest request;
	private HttpResponse response;

	public ControllerRouter(HttpRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
	}

	public void route() {
		HttpRouter router = getRouter();
		if (HttpMethod.POST == getRouter().getMethod()) {
			router.getController().doPost(request, response);
		} else if (HttpMethod.GET == router.getMethod()) {
			router.getController().doGet(request, response);
		} else {
			throw new NotFoundException("지원하지 않는 메소드입니다.");
		}
	}

	protected HttpRouter getRouter() {
		final String path = request.getPath();
		if ("/".equals(path) && HttpMethod.GET == request.getMethod())
			return ROOT_ROUTE;

		return routers.stream()
				.filter(router -> router.getMethod().equals(request.getMethod()))
				.filter(router -> (path.startsWith(router.getPath()) || path.endsWith(router.getPath())))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("지원하지 않는 기능입니다."));
	}
}

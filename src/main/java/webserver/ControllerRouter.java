package webserver;

import com.google.common.collect.Maps;
import controller.*;
import domain.HttpRequest;
import domain.UserAction;
import exception.NotFoundException;

import java.util.Map;

public class ControllerRouter {
	private static final Map<String, Controller> routeMap = Maps.newHashMap();

	static {
		routeMap.put(UserAction.JOIN.getUri(), new JoinController());
		routeMap.put(UserAction.LOGIN.getUri(), new LoginController());
		routeMap.put(UserAction.LIST.getUri(), new MembersController());
		routeMap.put(".css", new StaticResourceController());
		routeMap.put(".fonts", new StaticResourceController());
		routeMap.put(".images", new StaticResourceController());
		routeMap.put(".js", new StaticResourceController());
		routeMap.put(".html", new StaticResourceController());
		routeMap.put(".ico", new StaticResourceController());
	}

	private HttpRequest request;

	public ControllerRouter(HttpRequest request) {
		this.request = request;
	}

	public Controller get() {
		String routeKey = routeMap.keySet()
				.stream()
				.filter(key -> request.getPath().contains(key))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("지원하지 않는 기능입니다."));
		return routeMap.get(routeKey);
	}
}

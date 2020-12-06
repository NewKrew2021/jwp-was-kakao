package webserver;

import controller.*;
import domain.HttpResponse;
import exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static utils.RequestUtils.getHttpRequest;

public class ControllerRouterTest {

	@Test
	public void getControllerTest() {
		ControllerRouter router = new ControllerRouter(getHttpRequest("GET_Resource1"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(StaticResourceController.class);

		router = new ControllerRouter(getHttpRequest("GET_Resource2"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(StaticResourceController.class);

		router = new ControllerRouter(getHttpRequest("POST_Request1"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(JoinController.class);

		router = new ControllerRouter(getHttpRequest("POST_Request2"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(LoginController.class);

		router = new ControllerRouter(getHttpRequest("GET_Request3"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(MembersController.class);

		router = new ControllerRouter(getHttpRequest("GET_Request2"), new HttpResponse(null));
		assertThat(router.getRouter().getController()).isInstanceOf(RootController.class);
	}

	@Test
	public void invalidRequest() {
		ControllerRouter router = new ControllerRouter(getHttpRequest("GET_Invalid_Request"), new HttpResponse(null));
		assertThatThrownBy(() -> router.getRouter().getController())
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	public void notSupportMethodTest() {
		ControllerRouter router = new ControllerRouter(getHttpRequest("PUT_Invalid_Request"), new HttpResponse(null));
		assertThatThrownBy(() -> router.getRouter().getController())
				.isInstanceOf(NotFoundException.class);
	}
}

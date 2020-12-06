package controller;

import exception.InvalidRequestBodyException;
import exception.InvalidRequestException;
import exception.InvalidResourceException;
import exception.NotFoundException;
import org.junit.jupiter.api.Test;
import utils.HttpRequstParser;
import webserver.ControllerRouter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static utils.RequestUtils.getHttpRequest;

public class ControllerRouterTest {

	@Test
	public void getControllerTest() {
		ControllerRouter router = new ControllerRouter(getHttpRequest("GET_Resource1"));
		assertThat(router.get()).isInstanceOf(StaticResourceController.class);

		router = new ControllerRouter(getHttpRequest("GET_Resource2"));
		assertThat(router.get()).isInstanceOf(StaticResourceController.class);

		router = new ControllerRouter(getHttpRequest("POST_Request1"));
		assertThat(router.get()).isInstanceOf(JoinController.class);

		router = new ControllerRouter(getHttpRequest("POST_Request2"));
		assertThat(router.get()).isInstanceOf(LoginController.class);

		router = new ControllerRouter(getHttpRequest("GET_Request3"));
		assertThat(router.get()).isInstanceOf(MembersController.class);
	}

	@Test
	public void invalidRequest() {
		ControllerRouter router = new ControllerRouter(getHttpRequest("GET_Invalid_Request"));
		assertThatThrownBy(() -> router.get())
				.isInstanceOf(NotFoundException.class);
	}
}

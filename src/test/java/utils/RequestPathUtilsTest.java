package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathUtilsTest {
	@Test
	@DisplayName("Request Line에서 path 분리하기 테스트")
	public void getRequestPathTest() {
		assertThat(RequestPathUtils.getRequestPath("GET / HTTP/1.1")).isEqualTo("/");
		assertThat(RequestPathUtils.getRequestPath("GET /index.html HTTP/1.1")).isEqualTo("/index.html");
		assertThat(RequestPathUtils.getRequestPath("GET /test HTTP/1.1")).isEqualTo("/test");
		assertThat(RequestPathUtils.getRequestPath("")).isEqualTo("");
	}
}

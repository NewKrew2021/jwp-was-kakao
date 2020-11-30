package utils;

import domain.HttpRequestHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpRequestParserTest {
	private HttpRequstParser underTest;

	@BeforeEach
	public void before() {
		Reader reader = new StringReader("GET /index.html HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Accept: */*\n\n");

		underTest = new HttpRequstParser(new BufferedReader(reader));
	}
	@Test
	@DisplayName("요청헤더를 가져온다.")
	public void getRequestHeadersTest() {
		List<HttpRequestHeader> headers = underTest.getRequestHeaders();

		assertThat(headers).hasSize(3);
		headers.forEach(header -> System.out.println(header.toString()));
		assertThat(headers.get(0).toString()).isEqualTo(new HttpRequestHeader("Host", "localhost:8080").toString());
	}

	@Test
	@DisplayName("요청 requestPath를 얻을 수 있다.")
	public void getRequestPathTest() {
		underTest.getRequestHeaders();
		assertThat(underTest.getRequestPath()).isEqualTo("/index.html");
	}

	@Test
	@DisplayName("요청 url에서 쿼리 파라미터를 파싱한다.")
	public void getRequestParamters() {
		String input = "/user/create?userId=adeldel&password=adeldel&name=adeldel&email=adeldel.daum.net";
		Map<String, String> parameters = underTest.getRequstParameters(input);
		assertThat(parameters).hasSize(4);
		assertTrue(parameters.containsKey("userId"));
		assertTrue(parameters.containsKey("password"));
		assertTrue(parameters.containsKey("name"));
		assertTrue(parameters.containsKey("email"));
	}

}

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

	@Test
	@DisplayName("요청헤더를 가져온다.")
	public void getRequestHeadersTest() {
		setRequestData();
		List<HttpRequestHeader> headers = underTest.getRequestHeaders();

		assertThat(headers).hasSize(3);
		headers.forEach(header -> System.out.println(header.toString()));
		assertThat(headers.get(0).toString()).isEqualTo(new HttpRequestHeader("Host", "localhost:8080").toString());
	}

	@Test
	@DisplayName("요청 requestPath를 얻을 수 있다.")
	public void getRequestPathTest() {
		setRequestData();
		underTest.getRequestHeaders();
		assertThat(underTest.getRequestPath()).isEqualTo("/index.html");
	}

	@Test
	@DisplayName("요청 url에서 쿼리 파라미터를 파싱한다.")
	public void getRequestParamters() {
		setRequestData();
		String input = "userId=adeldel&password=adeldel&name=adeldel&email=adeldel@daum.net";
		Map<String, String> parameters = underTest.getRequstParameters(input);
		assertThat(parameters).hasSize(4);
		assertTrue(parameters.containsKey("userId"));
		assertTrue(parameters.containsKey("password"));
		assertTrue(parameters.containsKey("name"));
		assertTrue(parameters.containsKey("email"));
	}

	@Test
	@DisplayName("Post body 데이터를 가져온다.")
	public void getRequestBodyTest() {
		setPostRequestData();
		String requestBody = underTest.getRequestBody(underTest.getRequestHeaders());
		assertThat(requestBody).isEqualTo("userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");
	}

	private void setRequestData() {
		Reader reader = new StringReader("GET /index.html HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Accept: */*\n\n");

		underTest = new HttpRequstParser(new BufferedReader(reader));
	}

	private void setPostRequestData() {
		Reader reader = new StringReader("POST /user/create HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Length: 67\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n" +
				"userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");

		underTest = new HttpRequstParser(new BufferedReader(reader));
	}
}

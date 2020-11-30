package utils;

import domain.HttpRequestHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
}

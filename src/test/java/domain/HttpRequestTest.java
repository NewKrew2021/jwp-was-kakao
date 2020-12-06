package domain;

import org.junit.jupiter.api.Test;
import utils.HttpRequstParser;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		HttpRequest request = new HttpRequstParser(bufferedReader).getHttpRequest();

		HttpHeader header = getHttpHeader(request, "Connection");
		assertEquals(HttpMethod.POST, request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", header.getValue());
		assertEquals("javajigi", request.getParameter().get("userId"));
		assertEquals("password", request.getParameter().get("password"));
		assertEquals("JaeSung", request.getParameter().get("name"));
	}

	@Test
	public void request_POST2() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		HttpRequest request = new HttpRequstParser(bufferedReader).getHttpRequest();

		HttpHeader header = getHttpHeader(request, "Connection");
		assertEquals(HttpMethod.POST, request.getMethod());
		assertEquals("/user/create", request.getPath());
		assertEquals("keep-alive", header.getValue());
		assertEquals("javajigi", request.getParameter().get("userId"));
		assertEquals("password", request.getParameter().get("password"));
		assertEquals("JaeSung", request.getParameter().get("name"));
		assertEquals("1", request.getParameter().get("id"));
	}

	private HttpHeader getHttpHeader(HttpRequest request, String key) {
		return request.getHeaders()
				.stream()
				.filter(httpHeader -> httpHeader.getKey().equals(key))
				.findFirst().get();
	}
}

package utils;

import domain.HttpHeader;
import domain.HttpRequest;
import domain.ContentType;
import exception.InvalidRequestBodyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpRequstParserTest {
	private HttpRequstParser underTest;
	private HttpRequest httpRequest;

	@Test
	@DisplayName("요청헤더를 가져온다.")
	public void getRequestHeadersTest() {
		createGetRequestData();
		List<HttpHeader> headers = httpRequest.getHeaders();

		assertThat(headers).hasSize(3);
		headers.forEach(header -> System.out.println(header.toString()));
		assertThat(headers.get(0).toString()).isEqualTo(new HttpHeader("Host", "localhost:8080").toString());
	}

	@Test
	@DisplayName("요청 requestPath를 얻을 수 있다.")
	public void getRequestPathTest() {
		createGetRequestData();
		assertThat(httpRequest.getPath()).isEqualTo("/index.html");
	}

	@Test
	@DisplayName("요청 url에서 쿼리 파라미터를 파싱한다.")
	public void getRequestParamters() {
		createGetRequestData();
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
		createPostRequestData();
		String requestBody = httpRequest.getBody();
		assertThat(requestBody).isEqualTo("userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");
	}

	@Test
	@DisplayName("Post body 데이터를 가져오는데 실패한다.")
	public void getRequestBodyFailTest() {
		Reader reader = new StringReader("POST /user/create HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n" +
				"userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");

		underTest = new HttpRequstParser(new BufferedReader(reader));
		assertThatThrownBy(() -> underTest.requestParse())
				.isInstanceOf(InvalidRequestBodyException.class);
	}

	@Test
	@DisplayName("응답데이터의 컨텐츠 타입을 요청주소에 따라 변경한다.")
	public void getContentTypeTest() {
		setStyleSheetsData();
		assertThat(httpRequest.getContentType()).isEqualTo(ContentType.CSS);
	}

	@Test
	@DisplayName("쿼리파람 디코딩 테스트")
	public void decodeTest() {
		createPostRequestData();
		Map<String, String> requestParam = underTest.getRequstParameters(httpRequest.getBody());
		assertThat(underTest.decodeQueryParam(requestParam.get("email"))).isEqualTo("adel@daum.net");
		assertThat(underTest.decodeQueryParam("%EC%95%84%EB%8D%B8%EB%8D%B8")).isEqualTo("아델델");

	}

	@Test
	@DisplayName("파일확장자 따라 컨텐츠 타입을 구할수 있다.")
	public void contentTypeTest() {
		createPostRequestData();
		assertThat(underTest.getContentType("/index.html")).isEqualTo(ContentType.HTML);
		assertThat(underTest.getContentType("/styles.css")).isEqualTo(ContentType.CSS);
		assertThat(underTest.getContentType("/login.js")).isEqualTo(ContentType.JS);
		assertThat(underTest.getContentType("/")).isEqualTo(ContentType.HTML);
	}

	@Test
	@DisplayName("컨텐츠타입으로 form post 요청인지 확인한다.")
	public void isFormBodyRequestTest() {
		createPostRequestData();
		assertTrue(underTest.isFormBodyRequest(httpRequest));

		createGetRequestData();
		assertFalse(underTest.isFormBodyRequest(httpRequest));
	}

	private void createGetRequestData() {
		Reader reader = new StringReader("GET /index.html HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Accept: */*\n\n");

		underTest = new HttpRequstParser(new BufferedReader(reader));
		httpRequest = underTest.requestParse();
	}

	private void createPostRequestData() {
		Reader reader = new StringReader("POST /user/create HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Length: 67\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n" +
				"userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");

		underTest = new HttpRequstParser(new BufferedReader(reader));
		httpRequest = underTest.requestParse();
	}

	private void setStyleSheetsData() {
		Reader reader = new StringReader("GET /css/styles.css HTTP/1.1\n" +
				"Host: localhost:8080\n" +
				"Connection: keep-alive\n" +
				"Content-Type: application/x-www-form-urlencoded\n" +
				"Accept: */*\n\n");

		underTest = new HttpRequstParser(new BufferedReader(reader));
		httpRequest = underTest.requestParse();
	}
}

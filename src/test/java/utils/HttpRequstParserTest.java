package utils;

import domain.ContentType;
import domain.HttpHeader;
import domain.HttpRequest;
import exception.InvalidRequestBodyException;
import exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
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
		createPostRequestData("POST_Request1");
		Map<String, String> parameters = underTest.getRequstParameters(httpRequest.getBody());
		assertThat(parameters).hasSize(4);
		assertTrue(parameters.containsKey("userId"));
		assertTrue(parameters.containsKey("password"));
		assertTrue(parameters.containsKey("name"));
		assertTrue(parameters.containsKey("email"));
	}

	@Test
	@DisplayName("Post body 데이터를 가져온다.")
	public void getRequestBodyTest() {
		createPostRequestData("POST_Request1");
		String requestBody = httpRequest.getBody();
		assertThat(requestBody).isEqualTo("userId=adeldel&password=password&name=adeldel&email=adel%40daum.net");
	}

	@Test
	@DisplayName("Post body 데이터를 가져오는데 실패한다.")
	public void getRequestBodyFailTest() {
		BufferedReader reader = new BufferedReader(new StringReader(getRequestBody("POST_Invalid_Header")));
		assertThatThrownBy(() -> new HttpRequstParser(reader))
				.isInstanceOf(InvalidRequestBodyException.class);
	}

	@Test
	@DisplayName("헤더 정보가 정상인지 확인한다.")
	public void validateTest() {
		BufferedReader reader = new BufferedReader(new StringReader(getRequestBody("GET_Invalid_Header")));
		assertThatThrownBy(() -> new HttpRequstParser(reader))
				.isInstanceOf(InvalidRequestException.class);
	}

	@Test
	@DisplayName("응답데이터의 컨텐츠 타입을 요청주소에 따라 변경한다.")
	public void getContentTypeTest() {
		BufferedReader reader = new BufferedReader(new StringReader(getRequestBody("GET_Resource2")));
		underTest = new HttpRequstParser(new BufferedReader(reader));
		httpRequest = underTest.getHttpRequest();
		assertThat(httpRequest.getContentType()).isEqualTo(ContentType.CSS);
	}

	@Test
	@DisplayName("쿼리파람 디코딩 테스트")
	public void decodeTest() {
		createPostRequestData("POST_Request1");
		Map<String, String> requestParam = underTest.getRequstParameters(httpRequest.getBody());
		assertThat(underTest.decodeQueryParam(requestParam.get("email"))).isEqualTo("adel@daum.net");
		assertThat(underTest.decodeQueryParam("%EC%95%84%EB%8D%B8%EB%8D%B8")).isEqualTo("아델델");

	}

	@Test
	@DisplayName("파일확장자 따라 컨텐츠 타입을 구할수 있다.")
	public void contentTypeTest() {
		createPostRequestData("POST_Request1");
		assertThat(underTest.getContentType("/index.html")).isEqualTo(ContentType.HTML);
		assertThat(underTest.getContentType("/styles.css")).isEqualTo(ContentType.CSS);
		assertThat(underTest.getContentType("/login.js")).isEqualTo(ContentType.JS);
		assertThat(underTest.getContentType("/")).isEqualTo(ContentType.HTML);
	}

	@Test
	@DisplayName("컨텐츠타입으로 form post 요청인지 확인한다.")
	public void isFormBodyRequestTest() {
		createPostRequestData("POST_Request1");
		assertTrue(underTest.isFormBodyRequest(httpRequest));

		createGetRequestData();
		assertFalse(underTest.isFormBodyRequest(httpRequest));
	}

	private void createGetRequestData() {
		BufferedReader reader = new BufferedReader(new StringReader(getRequestBody("GET_Request1")));
		underTest = new HttpRequstParser(reader);
		httpRequest = underTest.getHttpRequest();
	}

	private void createPostRequestData(String filename) {
		BufferedReader reader = new BufferedReader(new StringReader(getRequestBody(filename)));
		underTest = new HttpRequstParser(reader);
		httpRequest = underTest.getHttpRequest();
	}

	private String getRequestBody(String path) {
		try {
			return new String(FileIoUtils.loadFileFromClasspath(path));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}
}

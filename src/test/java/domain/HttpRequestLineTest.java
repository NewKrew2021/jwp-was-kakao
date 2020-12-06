package domain;

import exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpRequestLineTest {
	@Test
	public void 첫번째_라인을_파싱해서_requestLine을_생성한다() {
		HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1\n");
		assertThat(httpRequestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
		assertThat(httpRequestLine.getUri().getPath()).isEqualTo("/index.html");
		assertThat(httpRequestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
	}

	@Test
	public void invalidateTest() {
		assertThatThrownBy(() -> new HttpRequestLine("GET /index.html\n"))
				.isInstanceOf(InvalidRequestException.class);

		assertThatThrownBy(() -> new HttpRequestLine("GET HTTP/1.1\n"))
				.isInstanceOf(InvalidRequestException.class);

		assertThatThrownBy(() -> new HttpRequestLine("MUST /index.html HTTP/1.1\n"))
				.isInstanceOf(InvalidRequestException.class);
	}

}

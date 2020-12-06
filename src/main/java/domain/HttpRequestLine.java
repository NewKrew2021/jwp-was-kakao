package domain;

import exception.InvalidRequestException;
import java.net.URI;

public class HttpRequestLine {
	private static final String DELEMITER = " ";
	private HttpMethod httpMethod;
	private String httpVersion;
	private URI uri;

	public HttpRequestLine(String requestLine) {
		String[] splitLine = requestLine.split(DELEMITER);
		validate(splitLine);
		setHttpMethod(splitLine[0].trim());
		setUri(splitLine[1].trim());
		setHttpVersion(splitLine[2].trim());
	}

	private void validate(String[] splitLine) {
		if (splitLine.length != 3)
			throw new InvalidRequestException("잘못된 요청입니다.");

	}
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String method) {
		this.httpMethod = HttpMethod.of(method);
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(String requestTarget) {
		this.uri = URI.create(requestTarget);
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
}

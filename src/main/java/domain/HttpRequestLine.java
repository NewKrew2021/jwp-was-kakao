package domain;

import exception.InvalidRequestException;

public class HttpRequestLine {
	private static final String DELEMITER = " ";
	private HttpMethod httpMethod;
	private String requestTarget;
	private String httpVersion;

	public HttpRequestLine(String requestLine) {
		String[] splitLine = requestLine.split(DELEMITER);
		validate(splitLine);
		setHttpMethod(splitLine[0].trim());
		setRequestTarget(splitLine[1].trim());
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

	public String getRequestTarget() {
		return requestTarget;
	}

	public void setRequestTarget(String requestTarget) {
		this.requestTarget = requestTarget;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
}

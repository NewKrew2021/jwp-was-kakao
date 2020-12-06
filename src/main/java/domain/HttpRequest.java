package domain;

import java.util.List;
import java.util.Map;

public class HttpRequest {
	private HttpMethod method;
	private List<HttpHeader> headers;
	private String path;
	private Map<String, String> parameter;
	private Map<String, String> cookies;
	private ContentType contentType;
	private String body;
	private String httpVersion;

	public HttpRequest(HttpMethod method, List<HttpHeader> headers, String path) {
		this.method = method;
		this.headers = headers;
		this.path = path;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public List<HttpHeader> getHeaders() {
		return headers;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public boolean isLoginCookie() {
		return cookies.keySet().stream()
				.filter(key -> "logined".equals(key) && "true".equals(cookies.get(key)))
				.findAny()
				.isPresent();
	}
}

package domain;

import java.util.List;
import java.util.Map;

public class HttpRequest {
	private HttpMethod method;
	private List<HttpHeader> headers;
	private String path;
	private Map<String, String> requestParam;
	private Map<String, String> cookies;
	private ContentType contentType;
	private String body;

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

	public Map<String, String> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, String> requestParam) {
		this.requestParam = requestParam;
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
}

package domain;

public class HttpRequestHeader {
	private String key;
	private String value;

	public HttpRequestHeader(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return key + ": " + value;
	}
}

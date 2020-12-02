package domain;

public enum ActionSegment {
	JOIN("/user/create"),
	LOGIN("/user/login"),
	LIST("/user/list");

	private final String uri;
	ActionSegment(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
}

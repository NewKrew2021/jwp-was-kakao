package domain;

public enum UserAction {
	JOIN("/user/create"),
	LOGIN("/user/login"),
	LIST("/user/list");

	private final String uri;

	UserAction(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
}

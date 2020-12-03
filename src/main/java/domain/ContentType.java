package domain;

public enum ContentType {
	HTML("text/html;charset=UTF-8"),
	CSS("text/css"),
	JS("application/js");

	private final String type;

	ContentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

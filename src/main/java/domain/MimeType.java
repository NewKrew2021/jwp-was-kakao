package domain;

import java.util.Arrays;

public enum MimeType {
	HTML("text/html;charset=UTF-8"),
	CSS("text/css"),
	JS("application/js");

	private final String type;

	MimeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

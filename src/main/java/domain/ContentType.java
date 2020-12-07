package domain;

import java.util.Arrays;

public enum ContentType {
	HTML("text/html;charset=UTF-8", "html"),
	CSS("text/css", "css"),
	JS("application/js", "js"),
	FORM("application/x-www-form-urlencoded", "form");

	private final String type;
	private final String subfix;

	ContentType(String type, String subfix) {
		this.type = type;
		this.subfix = subfix;
	}

	public String getType() {
		return type;
	}

	public String getSubfix() {
		return subfix;
	}

	public static ContentType of(String path) {
		return Arrays.stream(values())
				.filter(contentType -> path.endsWith(contentType.getSubfix()))
				.findFirst()
				.orElse(HTML);
	}
}

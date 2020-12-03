package domain;

import java.util.Arrays;

public enum HttpMethod {
	GET,PUT,POST,DELETE;

	public static HttpMethod of(String method) {
		return Arrays.stream(values())
				.filter(httpMethod -> httpMethod.name().equalsIgnoreCase(method))
				.findFirst()
				.orElse(GET);
	}
}

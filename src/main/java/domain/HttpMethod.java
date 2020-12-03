package domain;

import exception.InvalidRequestException;

import java.util.Arrays;

public enum HttpMethod {
	GET,PUT,POST,DELETE;

	public static HttpMethod of(String method) {
		return Arrays.stream(values())
				.filter(httpMethod -> httpMethod.name().equalsIgnoreCase(method))
				.findFirst()
				.orElseThrow(() -> new InvalidRequestException("잘못된 요청 Metohd 입니다."));
	}
}

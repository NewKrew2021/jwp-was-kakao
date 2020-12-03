package utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import domain.*;
import exception.InvalidRequestBodyException;
import exception.InvalidRequestException;
import exception.InvalidRequestHeaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class HttpRequstParser {
	public static final String HEADER_SPLIT_DELIMETER = ": ";
	private static final String COOKIE_DELEIMTER = ";";
	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String CONTENT_TYPE = "Content-Type";
	private BufferedReader bufferedReader;

	public HttpRequstParser(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public HttpRequest requestParse() {
		List<HttpHeader> headers = new ArrayList<>();
		try {
			String line = bufferedReader.readLine();
			HttpRequestLine requestLine = new HttpRequestLine(line);
			while (!"".equals(line = bufferedReader.readLine())) {
				validate(line);
				String[] header = line.split(HEADER_SPLIT_DELIMETER);
				headers.add(new HttpHeader(header[0].trim(), header[1].trim()));
			}
			return makeHttpRequest(headers, requestLine);
		} catch (IOException | InvalidRequestHeaderException ex) {
			throw new InvalidRequestException("요청 파싱하는 데이터에 문제가 있습니다.");
		}
	}

	protected void validate(String headerLine) {
		if (!headerLine.contains(HEADER_SPLIT_DELIMETER)) {
			throw new InvalidRequestHeaderException("잘못된 헤더정보 입니다.");
		}
		if (headerLine.split(HEADER_SPLIT_DELIMETER).length < 2) {
			throw new InvalidRequestHeaderException("잘못된 헤더정보 입니다.");
		}
	}

	private HttpRequest makeHttpRequest(List<HttpHeader> headers, HttpRequestLine httpRequestLine) {
		HttpRequest httpRequest = new HttpRequest(httpRequestLine.getHttpMethod(), headers, httpRequestLine.getRequestTarget());
		if (isFormBodyRequest(httpRequest)) {
			String requestBody = getRequestBody(headers);
			httpRequest.setBody(requestBody);
			httpRequest.setRequestParam(getQueryMap(requestBody));
			httpRequest.setHttpVersion(httpRequestLine.getHttpVersion());
		}
		httpRequest.setCookies(getCookies(headers));
		httpRequest.setContentType(getContentType(httpRequestLine.getRequestTarget()));
		return httpRequest;
	}

	protected String getRequestBody(List<HttpHeader> requestHeaders) {
		try {
			HttpHeader contentLengthHeader = requestHeaders.stream()
					.filter(header -> header.getKey().equalsIgnoreCase(CONTENT_LENGTH))
					.findFirst()
					.orElseThrow();
			return IOUtils.readData(bufferedReader, Integer.valueOf(contentLengthHeader.getValue()));
		} catch (IOException | NoSuchElementException e) {
			throw new InvalidRequestBodyException("fom body 데이터를 가져오는데 실패하였습니다.");
		}
	}

	protected Map<String, String> getRequstParameters(String requestBody) {
		if (requestBody != null && !requestBody.isEmpty()) {
			return getQueryMap(requestBody);
		}
		return new HashMap<>();
	}

	private Map<String, String> getQueryMap(String query) {
		if (query == null)
			return null;
		String[] params = query.split("&");
		Map<String, String> parameters = new HashMap<>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			parameters.put(name, decodeQueryParam(value));
		}
		return parameters;
	}

	protected String decodeQueryParam(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("문자 디코딩에 실패하였습니다.");
		}
	}
	private Map<String, String> getCookies(List<HttpHeader> headers) {
		HttpHeader cookieHeader = headers.stream()
				.filter(header -> "Cookie".equalsIgnoreCase(header.getKey()))
				.findFirst()
				.orElse(new HttpHeader("", ""));

		if (cookieHeader.getKey().isEmpty())
			return Maps.newHashMap();

		return Splitter.on(COOKIE_DELEIMTER).withKeyValueSeparator("=").split(cookieHeader.getValue());
	}

	public boolean isLoginCookie(Map<String, String> cookies) {
		return cookies.keySet().stream()
				.filter(key -> "logined".equals(key) && "true".equals(cookies.get(key)))
				.findAny()
				.isPresent();
	}

	protected ContentType getContentType(String path) {
		return ContentType.of(path);
	}

	protected boolean isFormBodyRequest(HttpRequest httpRequest) {
		return httpRequest.getMethod() == HttpMethod.POST
				&& httpRequest.getHeaders()
				.stream()
				.filter(header -> header.getKey().equalsIgnoreCase(CONTENT_TYPE))
				.filter(header -> header.getValue().equals(ContentType.FORM.getType()))
				.findFirst()
				.isPresent();
	}
}

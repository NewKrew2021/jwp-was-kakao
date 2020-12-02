package utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import domain.HttpHeader;
import domain.HttpMethod;
import domain.HttpRequest;
import domain.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequstParser {
	public static final String HEADER_SPLIT_DELIMETER = ": ";
	private static final String COOKIE_DELEIMTER = ";";
	private static final String DELEMITER = " ";
	private static final String CONTENT_LENGTH = "Content-Length";
	private BufferedReader bufferedReader;

	public HttpRequstParser(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public HttpRequest requestParse() {
		List<HttpHeader> headers = new ArrayList<>();
		try {
			String line = "";
			HttpMethod httpMethod = null;
			String requestPath = "";
			while (!"".equals(line = bufferedReader.readLine())) {
				if (line.contains(HEADER_SPLIT_DELIMETER)) {
					String[] header = line.split(HEADER_SPLIT_DELIMETER);
					headers.add(new HttpHeader(header[0], header[1]));
				} else {
					String[] firstLine = line.split(DELEMITER);
					httpMethod = HttpMethod.of(firstLine[0]);
					requestPath = firstLine[1];
				}
			}
			return makeHttpRequest(headers, httpMethod, requestPath);
		} catch (IOException ex) {
			throw new RuntimeException("요청 파싱하는 데이터에 문제가 있습니다.");
		}
	}

	private HttpRequest makeHttpRequest(List<HttpHeader> headers, HttpMethod httpMethod, String requestPath) {
		HttpRequest httpRequest = new HttpRequest(httpMethod, headers, requestPath);
		if (httpMethod == HttpMethod.POST) {
			String requestBody = getRequestBody(headers);
			httpRequest.setBody(requestBody);
			httpRequest.setRequestParam(getQueryMap(requestBody));
		}
		httpRequest.setCookies(getCookies(headers));
		httpRequest.setContentType(getMimeType(requestPath));
		return httpRequest;
	}

	private String getRequestBody(List<HttpHeader> requestHeaders) {
		try {
			HttpHeader contentLengthHeader = requestHeaders.stream()
					.filter(header -> header.getKey().equals(CONTENT_LENGTH))
					.findFirst()
					.orElse(new HttpHeader(CONTENT_LENGTH, "0"));
			return IOUtils.readData(bufferedReader, Integer.valueOf(contentLengthHeader.getValue()));
		} catch (IOException e) {
			throw new RuntimeException("컨텐츠 데이터를 가져오는데 오류입니다.");
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
				.filter(header -> "Cookie".equals(header.getKey()))
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

	private ContentType getMimeType(String path) {
		if (path.endsWith(".css"))
			return ContentType.CSS;
		if (path.endsWith(".js"))
			return ContentType.JS;
		return ContentType.HTML;
	}
}

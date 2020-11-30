package utils;

import domain.HttpMethod;
import domain.HttpRequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpRequstParser {
	public static final String HEADER_SPLIT_DELIMETER = ": ";
	private static final String DELEMITER = " ";
	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String CONTENT_TYPE_CSS = "text/css";
	private static final String CONENT_TYPE_JS = "text/javascript";
	private static final String CONTENT_TYPE_DEFAULT = "text/html;charset=UTF-8";
	private BufferedReader bufferedReader;
	private HttpMethod httpMethod;
	private String requestPath;

	public HttpRequstParser(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public List<HttpRequestHeader> getRequestHeaders() {
		List<HttpRequestHeader> headers = new ArrayList<>();
		try {
			String line = "";
			while (!"".equals(line = bufferedReader.readLine())) {
				if (line.contains(HEADER_SPLIT_DELIMETER)) {
					String[] header = line.split(HEADER_SPLIT_DELIMETER);
					headers.add(new HttpRequestHeader(header[0], header[1]));
				} else {
					String[] firstLine = line.split(DELEMITER);
					httpMethod = HttpMethod.of(firstLine[0]);
					requestPath = firstLine[1];
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return headers;
	}

	public String getRequestBody(List<HttpRequestHeader> requestHeaders) {
		try {
			HttpRequestHeader contentLengthHeader = requestHeaders.stream()
					.filter(header -> header.getKey().equals(CONTENT_LENGTH))
					.findFirst()
					.orElse(new HttpRequestHeader(CONTENT_LENGTH, "0"));
			return IOUtils.readData(bufferedReader, Integer.valueOf(contentLengthHeader.getValue()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public Map<String, String> getRequstParameters(String requestBody) {
		if (requestBody != null && !requestBody.isEmpty()) {
			return getQueryMap(requestBody);
		}
		return new HashMap<>();
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	private Map<String, String> getQueryMap(String query) {
		if (query == null)
			return null;
		String[] params = query.split("&");
		Map<String, String> parameters = new HashMap<>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			parameters.put(name, value);
		}
		return parameters;
	}

	public boolean isLoginCookie(List<HttpRequestHeader> headers) {
		return headers.stream()
				.filter(header -> "Cookie".equals(header.getKey())
						&& "logined=true".equals(header.getValue()))
				.findAny().isPresent();
	}

	public String getContentType() {
		if (requestPath.endsWith(".css"))
			return CONTENT_TYPE_CSS;
		if (requestPath.endsWith(".js"))
			return CONENT_TYPE_JS;
		return CONTENT_TYPE_DEFAULT;
	}
}

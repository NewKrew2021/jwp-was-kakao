package utils;

import domain.HttpRequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequstParser {
	public static final String HEADER_SPLIT_DELIMETER = ": ";
	private static final String DELEMITER = " ";
	private BufferedReader bufferedReader;
	private String httpMethod;
	private String requestPath;
	private Map<String, String> httpRequstParameters;

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
					httpMethod = firstLine[0];
					requestPath = firstLine[1];
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return headers;
	}

	public Map<String, String> getRequstParameters(String requestPath) {
		if (requestPath.contains("?")) {
			String[] queryParam = requestPath.split("\\?");
			this.httpRequstParameters = getQueryMap(queryParam[1]);
			return httpRequstParameters;
		}
		return new HashMap<>();
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	/** URL에서 파라미터를 파싱한다 **/
	private Map<String, String> getQueryMap(String query) {
		if (query == null)
			return null;

		int pos1 = query.indexOf("?");
		if (pos1 >= 0) {
			query = query.substring(pos1 + 1);
		}

		String[] params = query.split("&");
		Map<String, String> parameters = new HashMap<>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			parameters.put(name, value);
		}
		return parameters;
	}
}

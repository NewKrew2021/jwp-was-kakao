package utils;

import domain.HttpRequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequstParser {
	public static final String HEADER_SPLIT_DELIMETER = ": ";
	private static final String DELEMITER = " ";
	private BufferedReader bufferedReader;
	private String httpMethod;
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
					httpMethod = firstLine[0];
					requestPath = firstLine[1];
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return headers;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}
}

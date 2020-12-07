package utils;

import domain.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

public class RequestUtils {
	public static HttpRequest getHttpRequest(String path) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(new String(FileIoUtils.loadFileFromClasspath(path))));
			HttpRequstParser underTest = new HttpRequstParser(reader);
			return underTest.getHttpRequest();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}

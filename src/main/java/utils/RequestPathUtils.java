package utils;

import java.util.Arrays;
import java.util.List;

public class RequestPathUtils {
	private static final String TEMPLATES = "./templates";
	private static final String STATICS = "./static";
	private static final List<String> STATIC_LIST = Arrays.asList("/css", "/fonts", "/images", "/js");

	public static String getResourcePath(String requestPath) {
		if (requestPath == null || requestPath.isEmpty())
			return "";
		for(String staticPath : STATIC_LIST) {
			if (requestPath.startsWith(staticPath)) {
				return STATICS + requestPath;
			}
		}
		return TEMPLATES + requestPath;
	}
}

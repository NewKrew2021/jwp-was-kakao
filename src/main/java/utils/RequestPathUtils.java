package utils;

public class RequestPathUtils {
	private static final String DELEMITER = " ";

	public static String getRequestPath(String line) {
		String[] tokens = line.split(DELEMITER);
		return tokens.length > 1 ? tokens[1] : "";
	}
}

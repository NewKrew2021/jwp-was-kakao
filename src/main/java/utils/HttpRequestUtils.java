package utils;

import exception.InvalidRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestUtils {

    private static final int MINIMUM_LENGTH_OF_PARAM_EXIST = 2;
    private static final String DISTINGUISH_REGEX = "&";
    private static final String EQUALS_REGEX = "=";
    private static final String PARAMETER_START_REGEX = "\\?";
    private static final int PATH_INDEX_IN_URI = 0;
    private static final int PARAMS_INDEX_IN_URI = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static String extractPath(String uri) {
        return uri.split(PARAMETER_START_REGEX)[PATH_INDEX_IN_URI];
    }

    public static Optional<String> extractParams(String uri) {
        String[] splitLine = uri.split(PARAMETER_START_REGEX);
        if (splitLine.length < MINIMUM_LENGTH_OF_PARAM_EXIST) {
            return Optional.empty();
        }
        return Optional.of(splitLine[PARAMS_INDEX_IN_URI]);
    }

    public static Map<String, String> requestStringToMap(String line) {
        Map<String, String> result = new HashMap<>();
        String[] splitString = line.split(DISTINGUISH_REGEX);
        for (String pair : splitString) {
            String[] splitPair = pair.split(EQUALS_REGEX);
            validatePairExist(splitPair);
            result.put(splitPair[KEY_INDEX], splitPair[VALUE_INDEX]);
        }
        return result;
    }

    private static void validatePairExist(String[] splitPair) {
        if (splitPair.length < MINIMUM_LENGTH_OF_PARAM_EXIST) {
            throw new InvalidRequestException();
        }
    }

}

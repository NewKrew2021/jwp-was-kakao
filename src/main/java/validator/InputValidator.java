package validator;

import org.springframework.util.StringUtils;
import utils.MessageUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private InputValidator() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    private static final String REGEX_EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_EMAIL);

    public static boolean isValidEmpty(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        Matcher m = EMAIL_PATTERN.matcher(email);
        if (!m.matches()) {
            return false;
        }
        return true;
    }
}

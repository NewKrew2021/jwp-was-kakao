package model.factory;

import dto.ParamValue;
import model.User;
import utils.MessageUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {

    private UserFactory() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static User create(ParamValue paramValue) {
        return parse(paramValue);
    }

    public static String parserUserId(ParamValue paramValue) {
        return parseUserParam(paramValue).get(0);
    }

    public static String parserUserPassword(ParamValue paramValue) {
        return parseUserParam(paramValue).get(1);
    }

    private static User parse(ParamValue paramValue) {
        List<String> collect = parseUserParam(paramValue);

        return new User(collect.get(0), collect.get(1), collect.get(2), collect.get(3));
    }

    private static List<String> parseUserParam(ParamValue paramValue) {
        Class<User> userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();

        return Arrays.stream(fields)
                .map(Field::getName)
                .map(paramValue::getValue)
                .collect(Collectors.toList());
    }
}

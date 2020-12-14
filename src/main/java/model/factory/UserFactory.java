package model.factory;

import dto.ParamValue;
import model.User;
import utils.MessageUtils;

import java.util.List;

public class UserFactory {

    private UserFactory() {
        throw new IllegalStateException(MessageUtils.UTILITY_CLASS);
    }

    public static User create(ParamValue paramValue) {
        return parse(paramValue);
    }

    public static String parserUserId(ParamValue paramValue) {
        return paramValue.parseParam(User.class).get(0);
    }

    public static String parserUserPassword(ParamValue paramValue) {
        return paramValue.parseParam(User.class).get(1);
    }

    private static User parse(ParamValue paramValue) {
        List<String> collect = paramValue.parseParam(User.class);

        return new User(collect.get(0), collect.get(1), collect.get(2), collect.get(3));
    }
}

package model.factory;

import dto.ParamValue;
import model.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {

    public static User create(ParamValue paramValue) {
        return parse(paramValue);
    }

    private static User parse(ParamValue paramValue) {
        Class userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();

        List<String> collect = Arrays.stream(fields)
                .map(Field::getName)
                .map(paramValue::getValue)
                .collect(Collectors.toList());

        return new User(collect.get(0), collect.get(1), collect.get(2), collect.get(3));
    }
}

package model.factory;

import dto.ParamValue;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {

    @DisplayName("Reflection을 이용한 User 파싱 테스트 : 필드가 추가될 때 해당 케이스가 깨지면 안됨")
    @Test
    void parse() {
        Class userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();

        List<String> userFields = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());

        assertThat(userFields.get(0)).isEqualTo("userId");
        assertThat(userFields.get(1)).isEqualTo("password");
        assertThat(userFields.get(2)).isEqualTo("name");
        assertThat(userFields.get(3)).isEqualTo("email");
    }

    @DisplayName("User Map Param으로 User 생성 테스트")
    @Test
    void createUser() {
        Optional<ParamValue> paramValue = ParamValue.of(Optional.of(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"));

        User user = UserFactory.create(paramValue.get());
        assertThat(user).isEqualTo(
                new User("javajigi", "password", "박재성", "javajigi@slipp.net"));
    }

}

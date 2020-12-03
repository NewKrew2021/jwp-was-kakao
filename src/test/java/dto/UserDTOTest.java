package dto;

import model.User;
import model.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    @DisplayName("UserDTO 생성 테스트")
    @Test
    void create() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", "test");
        paramMap.put("password", "test123");
        paramMap.put("name", "테스트");
        paramMap.put("email", "test@daum.net");

        User user = UserFactory.create(new ParamValue(paramMap));
        UserDTO userDTO = UserDTO.of(user);

        assertThat(userDTO)
                .isEqualToComparingFieldByField(UserDTO.of(user));
    }

}

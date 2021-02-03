package user.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserCreateValueTest {
    private String testDirectory = "./src/test/resources/";
    private UserCreateValue user;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        user = new UserCreateValue(new HttpRequest(new FileInputStream(testDirectory + "Http_POST.txt")));
    }

    @Test
    @DisplayName("생성 테스트")
    void createTest() {
        assertThat(user.getUserId()).isEqualTo("javajigi");
    }
}
package user.view;

import db.DataBase;
import org.junit.jupiter.api.Test;
import user.model.User;

import static org.assertj.core.api.Assertions.assertThat;

public class UserViewTest {
    @Test
    void testUserListHtml() {
        DataBase.addUser(new User("testUser", "testPassword", "testName", "testEmail@email.com"));
        DataBase.addUser(new User("secondUser", "secondPassword", "secondName", "secondEmail@email.com"));
        assertThat(new String(UserView.getUserListHtml())).contains(
                "testUser", "testName", "testEmail@email.com",
                "secondUser", "secondName", "secondEmail@email.com");
    }

    @Test
    void testUserProfileHtml() {
        DataBase.addUser(new User("testUser", "testPassword", "testName", "testEmail@email.com"));
        assertThat(new String(UserView.getUserProfileHtml("testUser"))).contains("testName", "testEmail@email.com");
    }
}

package acceptance;

import io.restassured.RestAssured;
import model.User;
import org.junit.jupiter.api.Test;

public class AcceptanceTest {
    @Test
    public void indexTest() {
        RestAssured.when().get("/")
                .then().statusCode(200);
    }

    @Test
    public void newUserTest() {
        User user = new User("qqqq", "mypw", "qqqq", "qqq@qqq.qqq");
        RestAssured.with().body("userId=qqqq&password=mypw&name=qqqq&email=qqq@qqq.qqq")
                .when().post("/user/create")
                .then().statusCode(302);
    }

    @Test
    public void loginTest() {
        RestAssured.with().body("userId=nono&password=nono")
                .when().get("/user/login")
                .then().statusCode(200);
    }

    @Test
    public void login_failedTest() {
        RestAssured.with().body("userId=nononono&password=nononono")
                .then().statusCode(302);
    }

    @Test
    public void userListTest() {
        RestAssured.given().cookie("logined", "false")
                .get("/user/list")
                .then().statusCode(200);
    }
}

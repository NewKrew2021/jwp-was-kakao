package template;

import exception.TemplateEngineException;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HandlebarTemplateEngineTest {
	@Test
	public void applyTemplateExceptionTest() {
		List<User> userList = new ArrayList<>();
		userList.add(new User("adel", "1234", "adel", "email"));
		assertThatThrownBy(() -> HandlebarTemplateEngine.apply("/user/test", userList))
				.isInstanceOf(TemplateEngineException.class);
	}
}

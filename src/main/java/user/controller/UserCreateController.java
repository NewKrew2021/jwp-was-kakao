package user.controller;

import annotation.web.ResponseStatus;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import user.exceptions.InvalidFormInputException;
import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.http.AbstractController;
import webserver.http.Body;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserCreateValue createValue = new UserCreateValue(httpRequest);

        checkIsValidUserInput(createValue);

        if (userIdExists(createValue.getUserId())) {
            httpResponse.sendStatusWithBody(ResponseStatus.CONFLICT, new Body("userId already exists".getBytes(), "text/plain"));
            return;
        }

        UserService.insert(createValue);
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public boolean supports(String path) {
        return path.endsWith("/user/create");
    }

    private String checkIsValidUserInput(UserCreateValue createValue) {
        if (StringUtils.isBlank(createValue.getUserId())) {
            throw new InvalidFormInputException("Please check userId");
        }

        if (StringUtils.isBlank(createValue.getPassword())) {
            throw new InvalidFormInputException("Please check password");
        }

        if (StringUtils.isBlank(createValue.getName())) {
            throw new InvalidFormInputException("Please check name");
        }

        if (!isValidEmail(createValue.getEmail())) {
            throw new InvalidFormInputException("Please check email");
        }

        return null;
    }

    private boolean userIdExists(String userId) {
        return UserService.findById(userId) != null;
    }

    private boolean isValidEmail(String email) {
        String pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return email.matches(pattern);
    }
}

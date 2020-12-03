package webserver;

import dto.ParamValue;
import dto.UserDTO;
import dto.UsersDTO;
import model.HttpStatus;
import model.User;
import model.factory.UserFactory;
import service.UserService;
import utils.MessageUtils;

import java.util.*;

public class URIFactory {

    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_LIST_URL = "/user/list";

    private static final String LOCATION_FILED = "Location";

    private UserService userService = new UserService();

    public Response create(Request request) {
        String pathGateway = request.getPathGateway();

        if (ResponseHandler.isStaticFile(request.getURL())) {
            return Response.of(request, HttpStatus.HTTP_OK);

        } else if (pathGateway.equals(USER_CREATE_URL)) {
            return createUser(request);

        } else if (pathGateway.equals(USER_LOGIN_URL)) {
            return login(request);

        } else if (pathGateway.equals(USER_LIST_URL)) {
            return list(request);
        }

        return Response.of(request, HttpStatus.HTTP_OK);
    }

    public Response createUser(Request request) {
        Optional<ParamValue> optionalParamValue = request.getParamMap();
        if (!optionalParamValue.isPresent()) {
            throw new IllegalStateException(MessageUtils.PARAM_VALUE_IS_EMPTY);
        }

        User user = UserFactory.create(optionalParamValue.get());
        userService.create(user);
        return Response.ofDirect(request, ParamValue.of(LOCATION_FILED, "/index.html"));
    }

    public Response login(Request request) {
        Optional<ParamValue> optionalParamValue = request.getParamMap();

        if (optionalParamValue.isPresent()) {
            ParamValue paramMap = optionalParamValue.get();
            String userId = UserFactory.parserUserId(paramMap);
            String password = UserFactory.parserUserPassword(paramMap);

            boolean isLogin = userService.isLogin(userId, password);
            return Response.ofDirect(request, parseLoginParamValue(isLogin));
        }
        return Response.ofDirect(request, ParamValue.of(LOCATION_FILED, "/user/login.html"));
    }

    private ParamValue parseLoginParamValue(boolean setCookie) {
        Map<String, String> map = new HashMap<>();
        map.put(LOCATION_FILED, "/index.html");
        map.put("Set-Cookie", "logined=" + setCookie + "; Path=/");
        return new ParamValue(map);
    }

    public Response list(Request request) {
        if (request.isLogined()) {
            List<User> users = userService.getList();
            UsersDTO usersDTO = getUsersDTO(users);

            String view = ResponseHandler.createTemplatesView(USER_LIST_URL, usersDTO);
            return Response.of(request, HttpStatus.HTTP_OK, view);
        }
        return Response.ofDirect(request, ParamValue.of(LOCATION_FILED, "/index.html"));
    }

    public UsersDTO getUsersDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(UserDTO.of(user)));
        return new UsersDTO(userDTOS);
    }
}

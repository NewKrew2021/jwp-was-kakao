package service;

import db.DataBase;
import domain.HttpMethod;
import domain.HttpRequestHeader;
import model.User;
import utils.HttpRequstParser;

import java.util.List;
import java.util.Map;

public class MemberService {
	private static final String USER_JOIN_REQUEST = "/user/create";

	public boolean isMemberJoinRequst(HttpRequstParser requstParser) {
		return requstParser.getRequestPath().startsWith(USER_JOIN_REQUEST)
				&& HttpMethod.POST == requstParser.getHttpMethod();
	}

	public void joinMember(HttpRequstParser requstParser, List<HttpRequestHeader> headers) {
		String requestBody = requstParser.getRequestBody(headers);
		Map<String, String> memberInfo = requstParser.getRequstParameters(requestBody);
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}
}

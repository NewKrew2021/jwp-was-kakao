package service;

import db.DataBase;
import domain.HttpMethod;
import model.User;
import utils.HttpRequstParser;

import java.util.Map;

public class MemberService {
	private static final String USER_JOIN_REQUEST = "/user/create";
	private static final String USER_LOGIN_REQUEST = "/user/login";

	public boolean isMemberJoinRequst(HttpRequstParser requstParser) {
		return requstParser.getRequestPath().startsWith(USER_JOIN_REQUEST)
				&& HttpMethod.POST == requstParser.getHttpMethod();
	}

	public void joinMember(Map<String, String> memberInfo) {
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}

	public boolean memberLoginRequest(HttpRequstParser requstParser) {
		return requstParser.getRequestPath().startsWith(USER_LOGIN_REQUEST)
				&& HttpMethod.POST == requstParser.getHttpMethod();
	}

	public boolean memberLogin(Map<String, String> memberInfo) {
		User user = DataBase.findUserById(memberInfo.get("userId"));
		if (user == null) {
			return false;
		}
		return memberInfo.get("password").equals(user.getPassword());
	}
}

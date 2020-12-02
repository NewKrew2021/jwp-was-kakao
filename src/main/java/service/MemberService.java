package service;

import db.DataBase;
import domain.HttpMethod;
import domain.HttpRequest;
import model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemberService {
	private static final String USER_JOIN_REQUEST = "/user/create";
	private static final String USER_LOGIN_REQUEST = "/user/login";
	private static final String USER_LIST_REQUEST = "/user/list";

	public boolean isMemberJoinRequst(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(USER_JOIN_REQUEST)
				&& HttpMethod.POST == httpRequest.getMethod();
	}

	public void joinMember(Map<String, String> memberInfo) {
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}

	public boolean memberLoginRequest(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(USER_LOGIN_REQUEST)
				&& HttpMethod.POST == httpRequest.getMethod();
	}

	public boolean memberLogin(Map<String, String> memberInfo) {
		User user = DataBase.findUserById(memberInfo.get("userId"));
		if (user == null) {
			return false;
		}
		return memberInfo.get("password").equals(user.getPassword());
	}

	public boolean isMemberListRequest(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(USER_LIST_REQUEST)
				&& HttpMethod.GET == httpRequest.getMethod();
	}

	public List<User> getAllMembers() {
		return new LinkedList<>(DataBase.findAll());
	}
}

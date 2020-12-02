package service;

import db.DataBase;
import domain.UserAction;
import domain.HttpMethod;
import domain.HttpRequest;
import model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemberService {
	public boolean isJoinReq(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(UserAction.JOIN.getUri())
				&& HttpMethod.POST == httpRequest.getMethod();
	}

	public void joinMember(Map<String, String> memberInfo) {
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}

	public boolean isLoginReq(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(UserAction.LOGIN.getUri())
				&& HttpMethod.POST == httpRequest.getMethod();
	}

	public boolean memberLogin(Map<String, String> memberInfo) {
		User user = DataBase.findUserById(memberInfo.get("userId"));
		if (user == null) {
			return false;
		}
		return memberInfo.get("password").equals(user.getPassword());
	}

	public boolean isMembersReq(HttpRequest httpRequest) {
		return httpRequest.getPath().startsWith(UserAction.LIST.getUri())
				&& HttpMethod.GET == httpRequest.getMethod();
	}

	public List<User> getAllMembers() {
		return new LinkedList<>(DataBase.findAll());
	}
}

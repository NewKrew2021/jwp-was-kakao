package service;

import db.DataBase;
import model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemberService {
	public void joinMember(Map<String, String> memberInfo) {
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}

	public boolean memberLogin(Map<String, String> memberInfo) {
		User user = DataBase.findUserById(memberInfo.get("userId"));
		if (user == null) {
			return false;
		}
		return memberInfo.get("password").equals(user.getPassword());
	}

	public List<User> getAllMembers() {
		return new LinkedList<>(DataBase.findAll());
	}
}

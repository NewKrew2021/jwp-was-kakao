package service;

import db.DataBase;
import model.User;

import java.util.Map;

public class MemberService {
	public void joinMember(Map<String, String> memberInfo) {
		User user = new User(memberInfo.get("userId"), memberInfo.get("password"), memberInfo.get("name"), memberInfo.get("email"));
		DataBase.addUser(user);
	}
}

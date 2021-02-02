package model;

public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        this.userId = validateId(userId);
        this.password = validatePw(password);
        this.name = validateName(name);
        this.email = validateEmail(email.replace("%40", "@"));
    }

    private String validatePw(String password) {
        if(!password.matches("^[\\w]{1,30}$")){
            throw new RuntimeException("올바르지 않은 비밀번호입니다.");
        }
        return password;
    }

    private String validateId(String userId) {
        if(!userId.matches("^[\\w]{1,30}$"))
            throw new RuntimeException("올바르지 않은 ID입니다.");
        return userId;
    }

    private String validateName(String name) {
        if(!name.matches("^[\\w]{1,30}$")){
            throw new RuntimeException("올바르지 않은 이름입니다.");
        }
        return name;
    }

    private String validateEmail(String email) {
        if(!email.matches("^[\\w]{1,30}@[\\w]{1,30}.[\\w]{1,30}$")){
            throw new RuntimeException("올바르지 않은 이메일입니다.");
        }
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}

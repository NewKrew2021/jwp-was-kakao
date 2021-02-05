package vo;

import model.User;

public class UserSessionVO {
    private String userId;
    private String userName;
    private String userEmail;

    public UserSessionVO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
    }
}

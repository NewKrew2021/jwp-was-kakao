package apps.slipp.db;

import com.google.common.collect.Maps;
import apps.slipp.model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    /**
     * for test. test 시 mock 을 사용하면 되지만 mock 은 강의범주를 벗어나는 것이라 추가합니다.
     */
    public static void removeAll(){
        users.clear();
    }
}

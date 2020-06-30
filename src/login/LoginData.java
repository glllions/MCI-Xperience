package login;

import records.User;

import java.util.Arrays;
import java.util.List;

public class LoginData {

    private static User loggedInUser=new User("","");

    private static List<User> users = Arrays.asList(
            new User("Student", "123"),
            new User("Pförtner", "123")
    );

    public static List<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser=user;
    }
}

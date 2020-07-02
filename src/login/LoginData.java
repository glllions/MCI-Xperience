package login;

import records.Person;
import records.Role;
import records.User;

import java.util.Arrays;
import java.util.List;

public class LoginData {

    private static User loggedInUser = new User("","", new Person("", "", 0, null));

    private static List<User> users = Arrays.asList(
            new User("Student", "123", new Person("Musterstudent", "Max", 0123, Role.STUDENT)),
            new User("Pförtner", "123", new Person("Musterpförtner", "Max", 0124, Role.PFOERTNER)),
            new User("Dozent", "123", new Person("Musterdozent", "Max", 0125, Role.DOZENT)),
            new User("Mitarbeiter", "123", new Person("Mustermitarbeiter", "Max", 0126, Role.MITARBEITER))
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

package records.data;

import records.Person;
import records.Role;

import java.util.Arrays;
import java.util.List;

public class PersonData {

    private static List<Person> persons = Arrays.asList(
            new Person("Musterstudent", "Max", 0123, Role.STUDENT),
            new Person("Musterpförtner", "Max", 0124, Role.PFOERTNER),
            new Person("Musterdozent", "Max", 0125, Role.DOZENT),
            new Person("Mustermitarbeiter", "Max", 0126, Role.MITARBEITER)
    );

    public static List<Person> getPersons() {
        return persons;
    }
}

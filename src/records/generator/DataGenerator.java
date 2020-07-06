package records.generator;

import records.*;
import records.data.PersonData;
import records.data.RoomData;
import records.data.TransponderData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    Random random = new Random();
    ArrayList<String> forenames = new ArrayList<>();
    ArrayList<String> surenames = new ArrayList<>();

    int gmidCounter = 1300;
    int matNrCounter = 1113000;

    char[] transponderAlphabet = {'X', 'C', 'Z', 'A', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    public DataGenerator(){
        initRandomNameData();

        generateRandomPersons(10);
        generateRandomRooms(10);
        generateRandomTransponders(10);

//        linkRandomTranspondersAndRooms();
//        linkRandomAuthorizations();
//        linkRandomLendings();
    }

    private void generateRandomPersons(int x) {
        List<Person> generated = new ArrayList();
        for (int i = 0; i < x; i++) {
            generated.add(getRandomPerson());
        }
        PersonData.getPersons().addAll(generated);
    }

    private void generateRandomRooms(int x) {
        List<Room> generated = new ArrayList();
        for (int i = 0; i < x; i++) {
            generated.add(getRandomRoom());
        }
        RoomData.getRooms().addAll(generated);
    }

    private void generateRandomTransponders(int x) {
        List<Transponder> generated = new ArrayList();
        for (int i = 0; i < x; i++) {
            generated.add(getRandomTransponder());
        }
        TransponderData.getTransponders().addAll(generated);
    }

    /**
     * Initalisieren der Dummy Daten für eine Zufallsgenerierung von Namen
     */
    private void initRandomNameData() {
        forenames.add("Mohammed");
        forenames.add("Peter");
        forenames.add("Alan");
        forenames.add("Edsger");
        forenames.add("Timo");

        surenames.add("Lee");
        surenames.add("Pan");
        surenames.add("Turing");
        surenames.add("Dijkstra");
        surenames.add("Dick");
    }


    /**
     * Anlegen eines Zufallsnamens
     *
     * @return ein Zufallsname als {@link Name}
     */
    public Name getMeSomeRandomNames(){
        Random random = new Random();
        String randomForeName = forenames.get(random.nextInt(forenames.size()));
        String randomSurename = surenames.get(random.nextInt(surenames.size()));

        return new Name(randomForeName, randomSurename);
    }


    //TODO: Put here some more Random Generation Functions



    public int createRandomRoomNumber(){
        int number = random.nextInt(4000);
        return number;
    }

    public Building getRandomBuilding(){
        Building randomBuilding = Building.values()[random.nextInt(Building.values().length)];
        return randomBuilding;
    }

    public Person getMeSomeRandomDozent(){
        Name name = new Name(getMeSomeRandomNames().getForename(), getMeSomeRandomNames().getSurename());
        Person person = new Person(name.getSurename(), name.getForename(), 123, Role.DOZENT);
        return person;
    }

    public Person getRandomPerson(){
        Name name = new Name(getMeSomeRandomNames().getForename(), getMeSomeRandomNames().getSurename());
        Role role = Role.values()[random.nextInt(Role.values().length)];
        Person person = new Person(name.getSurename(), name.getForename(), ++gmidCounter, role);
        if (role == Role.STUDENT) {
            person.matNrProperty().setValue(++matNrCounter);
        }
        return person;
    }

    public Room getRandomRoom(){
        Room room = new Room(createRandomRoomNumber(), getRandomBuilding());
        return room;
    }

    public Transponder getRandomTransponder(){
        StringBuilder name = new StringBuilder("T");
        for (int i = 0; i < 7; i++) {
            name.append(transponderAlphabet[random.nextInt(transponderAlphabet.length)]);
        }
        Transponder transponder = new Transponder(name.toString(), false);
        return transponder;
    }


}

/**
 * Wrapper Klasse für Namen bestehend aus Vor- und Nachname.
 */
class Name{
    private String forename = "Ive got no forename";
    private String surename = "Ive got no surename";

    public Name(String forename, String surename){
        this.forename = forename;
        this.surename = surename;
    }

    public String getForename() {
        return forename;
    }

    public String getSurename() {
        return surename;
    }

    private String getFullName(String forename, String surename){
        String name;
        return name=(forename+surename);
    }
}


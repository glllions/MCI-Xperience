package records;
import java.util.ArrayList;
import java.util.Random;

public class DataGenerator {

    Random random = new Random();
    ArrayList<String> forenames = new ArrayList<>();
    ArrayList<String> surenames = new ArrayList<>();


    public DataGenerator(){
        initRandomNameData();
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
        int number = random.nextInt(100);
        return number;
    }

    public String createRandomRoomName(){
        String pname = new String();
        return pname;
    }

    public User getMeSomeRandomDozent(){
        Name name = new Name(getMeSomeRandomNames().getForename(), getMeSomeRandomNames().getSurename());
        String pname=("Prof."+ name.getForename()+name.getSurename());
        User user = new User(pname);
        return user;
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


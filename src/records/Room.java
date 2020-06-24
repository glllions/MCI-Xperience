package records;


public class Room {

    private int number;
    private String name;
    private User user;


    public Room(int nnumber, String nname, User nuser){
        this.name= nname;
        this.number= nnumber;
        this.user= nuser;
    }
}

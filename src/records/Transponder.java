package records;


import java.util.List;

public class Transponder {

    private String name;
    private boolean status;

    public Transponder (String name, boolean status){
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

}

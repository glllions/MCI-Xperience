package records;


import javafx.beans.property.*;

public class Room {

    private IntegerProperty numberProperty = new SimpleIntegerProperty();
    private ObjectProperty<Building> buildingProperty = new SimpleObjectProperty();


    public Room(int number, Building building) {
        this.numberProperty.setValue(number);
        this.buildingProperty.setValue(building);
    }

    public IntegerProperty numberProperty() {
        return numberProperty;
    }

    public ObjectProperty<Building> buildingProperty() {
        return buildingProperty;
    }
}

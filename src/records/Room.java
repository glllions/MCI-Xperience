package records;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Room {

    private IntegerProperty numberProperty = new SimpleIntegerProperty();
    private ObjectProperty<Building> buildingProperty = new SimpleObjectProperty();
    private ObservableList<Person> roomManagersProperty = FXCollections.observableArrayList();
    private ObservableList<TransponderRoomLinking> linkingsProperty = FXCollections.observableArrayList();
    private ObservableList<Authorization> authorizationsProperty = FXCollections.observableArrayList();
    private ObservableList<Lending> lendingsProperty = FXCollections.observableArrayList();

    /**
     * Raum
     *
     * @param number Raumnummer
     * @param building Gebäude (z.B. Building.MENSA)
     */
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

    public ObservableList<Person> roomManagersProperty() {
        return roomManagersProperty;
    }

    public ObservableList<TransponderRoomLinking> linkingsProperty() {
        return linkingsProperty;
    }

    public ObservableList<Authorization> authorizationsProperty() {
        return authorizationsProperty;
    }

    public ObservableList<Lending> lendingsProperty() {
        return lendingsProperty;
    }

    public ObservableList<Lending> lastActivitiesProperty() {
        return FXCollections.observableList(lendingsProperty.stream()
                .sorted(Comparator.comparing(o -> o.beginDateProperty().getValue()))
                .limit(10)
                .collect(Collectors.toList()));
    }
}

package records;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Authorization {

    private ObjectProperty<Person> personProperty = new SimpleObjectProperty();
    private ObjectProperty<Room> roomProperty = new SimpleObjectProperty();
    private ObjectProperty<Date> endDateProperty = new SimpleObjectProperty();

    public Authorization(Person person, Room room, Date end) {
        this.personProperty.setValue(person);
        this.roomProperty.setValue(room);
        this.endDateProperty.setValue(end);

        link();
    }

    public ObjectProperty<Person> personProperty() {
        return personProperty;
    }

    public ObjectProperty<Room> roomProperty() {
        return roomProperty;
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDateProperty;
    }

    private void link() {
        System.out.println("Authorization: " + personProperty.getValue() + " GMID: " + personProperty.getValue().gmidProperty().getValue() + " ||  " + roomProperty.getValue().numberProperty().getValue());
        personProperty.getValue().authorizationsProperty().add(this);
        roomProperty.getValue().authorizationsProperty().add(this);
    }
}

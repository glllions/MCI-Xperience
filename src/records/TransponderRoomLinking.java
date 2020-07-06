package records;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TransponderRoomLinking {

    private ObjectProperty<Transponder> transponderProperty = new SimpleObjectProperty();
    private ObjectProperty<Room> roomProperty = new SimpleObjectProperty();

    public TransponderRoomLinking(Transponder transponder, Room room) {
        this.transponderProperty.setValue(transponder);
        this.roomProperty.setValue(room);

        link();
    }

    public ObjectProperty<Transponder> transponderProperty() {
        return transponderProperty;
    }

    public ObjectProperty<Room> roomProperty() {
        return roomProperty;
    }

    private void link() {
        transponderProperty.getValue().linkingsProperty().add(this);
        roomProperty.getValue().linkingsProperty().add(this);
    }
}

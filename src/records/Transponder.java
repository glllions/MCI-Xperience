package records;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transponder {

    private StringProperty nameProperty = new SimpleStringProperty();
    private BooleanProperty statusProperty = new SimpleBooleanProperty();

    public Transponder(String name, boolean status) {
        this.nameProperty.setValue(name);
        this.statusProperty.setValue(status);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public BooleanProperty statusProperty() {
        return statusProperty;
    }
}

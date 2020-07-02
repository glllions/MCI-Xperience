package records;

import javafx.beans.property.*;

public class Person {

    private StringProperty lastNameProperty = new SimpleStringProperty();
    private StringProperty preNameProperty = new SimpleStringProperty();
    private IntegerProperty gmidProperty = new SimpleIntegerProperty();
    private ObjectProperty<Role> roleProperty = new SimpleObjectProperty();

    public Person(String lastName, String preName, int gmid, Role role) {
        this.lastNameProperty.setValue(lastName);
        this.preNameProperty.setValue(preName);
        this.gmidProperty.setValue(gmid);
        this.roleProperty.setValue(role);
    }

    public StringProperty lastNameProperty() {
        return lastNameProperty;
    }

    public StringProperty preNameProperty() {
        return preNameProperty;
    }

    public IntegerProperty gmidProperty() {
        return gmidProperty;
    }

    public ObjectProperty<Role> roleProperty() {
        return roleProperty;
    }
}

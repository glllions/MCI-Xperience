package records;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Person {

    private StringProperty lastNameProperty = new SimpleStringProperty();
    private StringProperty preNameProperty = new SimpleStringProperty();
    private IntegerProperty gmidProperty = new SimpleIntegerProperty();
    private IntegerProperty matNrProperty = new SimpleIntegerProperty();
    private ObjectProperty<Role> roleProperty = new SimpleObjectProperty();
    private ObservableList<Authorization> authorizationsProperty = FXCollections.observableArrayList();
    private ObservableList<Lending> lendingsProperty = FXCollections.observableArrayList();

    /**
     * Person
     *
     * @param lastName Nachname
     * @param preName Vorname
     * @param gmid GMID
     * @param role Rolle (z.B. Role.STUDENT)
     */
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

    public IntegerProperty matNrProperty() {
        return matNrProperty;
    }

    public ObjectProperty<Role> roleProperty() {
        return roleProperty;
    }

    public ObservableList<Authorization> authorizationsProperty() {
        return authorizationsProperty;
    }

    public ObservableList<Lending> lendingsProperty() {
        return lendingsProperty;
    }

    public ObservableList<Lending> lastLendingsProperty() {
        return FXCollections.observableList(lendingsProperty.stream()
                .sorted(Comparator.comparing(o -> o.beginDateProperty().getValue()))
                .limit(10)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return preNameProperty.getValue() + " " + lastNameProperty.getValue();
    }
}

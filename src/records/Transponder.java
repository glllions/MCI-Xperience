package records;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Transponder {

    private StringProperty nameProperty = new SimpleStringProperty();
    private BooleanProperty availableProperty = new SimpleBooleanProperty();
    private ObservableList<TransponderRoomLinking> linkingsProperty = FXCollections.observableArrayList();
    private ObservableList<Lending> lendingsProperty = FXCollections.observableArrayList();

    /**
     * Transponder
     *
     * @param name Name/Nummer
     * @param available Status: Verfügbar/Verliehen
     */
    public Transponder(String name, boolean available) {
        this.nameProperty.setValue(name);
        this.availableProperty.setValue(available);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public BooleanProperty availableProperty() {
        return availableProperty;
    }

    public ObservableList<TransponderRoomLinking> linkingsProperty() {
        return linkingsProperty;
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

    @Override
    public String toString() {
        return nameProperty.getValue();
    }
}

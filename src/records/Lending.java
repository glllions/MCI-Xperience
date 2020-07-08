package records;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Lending {

    private ObjectProperty<Person> personProperty = new SimpleObjectProperty();
    private ObjectProperty<Transponder> transponderProperty = new SimpleObjectProperty();
    private ObjectProperty<Date> beginDateProperty = new SimpleObjectProperty();
    private ObjectProperty<Date> endDateProperty = new SimpleObjectProperty();

    /**
     * Ausleihe
     *
     * @param person      Ausleihender
     * @param transponder Ausgeliehener Transponder
     * @param begin       Datum der Ausleihe
     */
    public Lending(Person person, Transponder transponder, Date begin) {
        this.personProperty.setValue(person);
        this.transponderProperty.setValue(transponder);
        this.beginDateProperty.setValue(begin);

        link(false);
    }

    /**
     * Ausleihe
     *
     * @param person      Ausleihender
     * @param transponder Ausgeliehener Transponder
     * @param begin       Datum der Ausleihe
     * @param end         Datum der Rückgabe
     */
    public Lending(Person person, Transponder transponder, Date begin, Date end) {
        this.personProperty.setValue(person);
        this.transponderProperty.setValue(transponder);
        this.beginDateProperty.setValue(begin);
        this.endDateProperty.setValue(end);

        link(true);
    }

    public ObjectProperty<Person> personProperty() {
        return personProperty;
    }

    public ObjectProperty<Transponder> transponderProperty() {
        return transponderProperty;
    }

    public ObjectProperty<Date> beginDateProperty() {
        return beginDateProperty;
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDateProperty;
    }

    public void returnLending() {
        endDateProperty.setValue(new Date(System.currentTimeMillis()));
        transponderProperty.getValue().availableProperty().setValue(true);
    }

    private void link(boolean returned) {
        if (returned || transponderProperty.getValue().availableProperty().get()) {
            System.out.println("Lending: " + personProperty.getValue() + " GMID: " + personProperty.getValue().gmidProperty().getValue() + " ||  " + transponderProperty.getValue().nameProperty().getValue());
            personProperty.getValue().lendingsProperty().add(this);
            transponderProperty.getValue().lendingsProperty().add(this);
            transponderProperty.getValue().linkingsProperty()
                    .forEach(transponderRoomLinking -> transponderRoomLinking.roomProperty().getValue().lendingsProperty().add(Lending.this));
            if (!returned) {
                transponderProperty.getValue().availableProperty().setValue(false);
            }
        } else {
            System.out.println("Lending not possible. " + transponderProperty.getValue().nameProperty().getValue() + " is not available.");
        }
    }
}

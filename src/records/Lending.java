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
     * @param person Ausleihender
     * @param transponder Ausgeliehener Transponder
     * @param begin Datum der Ausleihe
     */
    public Lending(Person person, Transponder transponder, Date begin) {
        this.personProperty.setValue(person);
        this.transponderProperty.setValue(transponder);
        this.beginDateProperty.setValue(begin);

        link();
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

    private void link() {
        personProperty.getValue().lendingsProperty().add(this);
        transponderProperty.getValue().lendingsProperty().add(this);
    }
}

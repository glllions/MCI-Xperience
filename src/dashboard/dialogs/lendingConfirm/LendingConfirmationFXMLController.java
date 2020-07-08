package dashboard.dialogs.lendingConfirm;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import records.Lending;
import records.Person;
import records.Transponder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LendingConfirmationFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Label labelTransponder;

    @FXML
    private Label labelPerson;

    @FXML
    private Label labelDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");

    private static Date currentDate;

    private static Transponder currentTransponder;

    private static Person currentPerson;

    public static void setCurrentTransponder(Transponder currentTransponder) {
        LendingConfirmationFXMLController.currentTransponder = currentTransponder;
    }

    public static void setCurrentPerson(Person currentPerson) {
        LendingConfirmationFXMLController.currentPerson = currentPerson;
    }

    public static void setCurrentDate(Date currentDate) {
        LendingConfirmationFXMLController.currentDate = currentDate;
    }

    @FXML
    private void initialize() {
        initLabels();
    }

    private void initLabels() {
        labelTransponder.setText(String.valueOf(currentTransponder.nameProperty().getValue()));
        labelPerson.setText(currentPerson.toString());
        labelDate.setText(dateFormat.format(currentDate));
    }
}

package dashboard.dialogs.scannedTransponder;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import records.*;

import java.text.SimpleDateFormat;

public class ScannedTransponderDetailsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Label labelName;

    @FXML
    private Label labelBeginDate;

    @FXML
    private Label labelPerson;

    @FXML
    private Label labelEndDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");

    private static Transponder currentTransponder;

    public static void setCurrentTransponder(Transponder currentTransponder) {
        ScannedTransponderDetailsFXMLController.currentTransponder = currentTransponder;
    }

    @FXML
    private void initialize() {
        initLabels();
    }

    private void initLabels() {
        Lending openLending = currentTransponder.lendingsProperty().stream()
                .filter(lending -> lending.endDateProperty().isNull().get())
                .findFirst().get();

        labelName.setText(String.valueOf(currentTransponder.nameProperty().getValue()));
        labelBeginDate.setText(dateFormat.format(openLending.beginDateProperty().getValue()));
        labelPerson.setText(openLending.personProperty().get().toString());
        labelEndDate.textProperty().bind(Bindings.createStringBinding(
                () -> openLending.endDateProperty().isNull().get() ? "" : dateFormat.format(openLending.endDateProperty().getValue()),
                openLending.endDateProperty())
        );
    }
}

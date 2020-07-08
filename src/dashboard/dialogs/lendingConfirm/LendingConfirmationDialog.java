package dashboard.dialogs.lendingConfirm;

import dashboard.DashboardFXMLController;
import dashboard.dialogs.scannedPerson.ScannedPersonDialog;
import dashboard.persons.detail.PersonDetailsFXMLController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import records.Lending;
import records.Person;
import records.Transponder;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class LendingConfirmationDialog extends Dialog {

    private Person person;

    private Transponder transponder;

    private Date date;

    private StackPane root;

    public LendingConfirmationDialog(Person person, Transponder transponder) {
        super();
        this.person = person;
        this.transponder = transponder;
        this.date = new Date(System.currentTimeMillis());

        init();
    }

    private void setDetailViewFXMLChild() {
        try {
            getDialogPane().setContent(FXMLLoader.load(LendingConfirmationDialog.class.getResource("/dashboard/dialogs/lendingConfirm/lendingConfirmation.fxml")));
        } catch (IOException e) {
            Logger.getLogger(LendingConfirmationDialog.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
            e.printStackTrace();
        }
    }

    private void init() {

        LendingConfirmationFXMLController.setCurrentPerson(person);
        LendingConfirmationFXMLController.setCurrentTransponder(transponder);
        LendingConfirmationFXMLController.setCurrentDate(date);

        setTitle("Verleih bestätigen");

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        getDialogPane().getButtonTypes().add(ButtonType.APPLY);

        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        Button applyButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        applyButton.setText("Bestätigen");
        applyButton.setOnAction(event -> confirmLending());

        setDetailViewFXMLChild();

    }

    private void confirmLending() {
        new Lending(person, transponder, date);
        DashboardFXMLController.setDashboardMessage("Verleih erfolgreich!");
    }
}

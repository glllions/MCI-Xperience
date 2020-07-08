package dashboard.dialogs.scannedTransponder;

import dashboard.DashboardFXMLController;
import dashboard.dialogs.scannedPerson.ScannedPersonDetailsFXMLController;
import dashboard.persons.PersonsFXMLController;
import dashboard.persons.detail.PersonDetailsFXMLController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import records.Person;
import records.Transponder;

import java.io.IOException;
import java.util.logging.Logger;

public class ScannedTransponderDialog extends Dialog {

    private Transponder scannedTransponder;

    private StackPane root;

    public ScannedTransponderDialog(Transponder scannedTransponder) {
        super();
        this.scannedTransponder = scannedTransponder;

        ScannedTransponderDetailsFXMLController.setCurrentTransponder(scannedTransponder);
        init();
    }

    private void init() {

        setTitle("Transponder wird gescannt...");
        setHeaderText("Transponder wird gescannt...");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(200, 200);
        StackPane waitingBox = new StackPane(progressIndicator);
        waitingBox.setPadding(new Insets(100));
        waitingBox.setAlignment(Pos.CENTER);

        getDialogPane().setContent(waitingBox);
        getDialogPane().getScene().getWindow().sizeToScene();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> {
            setTitle("Eingescannter Transponder");
            setHeaderText(null);

            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            Button closeButton = (Button) getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());

            setDetailViewFXMLChild();

            returnTransponder();

            getDialogPane().getScene().getWindow().sizeToScene();
        });
        new Thread(sleeper).start();
    }

    private void returnTransponder() {
        scannedTransponder.lendingsProperty().stream()
                .filter(lending -> lending.endDateProperty().isNull().get())
                .forEach(lending -> lending.returnLending());
    }

    private void setDetailViewFXMLChild() {
        try {
            getDialogPane().setContent(FXMLLoader.load(ScannedTransponderDialog.class.getResource("/dashboard/dialogs/scannedTransponder/scannedTransponderDetails.fxml")));
        } catch (IOException e) {
            Logger.getLogger(ScannedTransponderDialog.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
            e.printStackTrace();
        }
    }
}

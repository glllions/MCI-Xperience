package dashboard.dialogs.scannedPerson;

import dashboard.DashboardFXMLController;
import dashboard.dialogs.lendingConfirm.LendingConfirmationDialog;
import dashboard.persons.detail.PersonDetailsFXMLController;
import dashboard.transponders.TranspondersFXMLController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import records.Person;
import records.Room;
import records.Transponder;
import records.data.PersonData;
import records.data.TransponderData;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ScannedPersonDialog extends Dialog {

    private Person scannedPerson;

    private Transponder transponder;

    private Random random = new Random();

    public ScannedPersonDialog(Person scannedPerson, Transponder transponder) {
        super();
        this.scannedPerson = scannedPerson;
        this.transponder = transponder;

        if (scannedPerson == null) {
            initScanRequest();
        } else {
            initScanned();
        }
    }

    private void setDetailViewFXMLChild() {
        try {
            getDialogPane().setContent(FXMLLoader.load(ScannedPersonDialog.class.getResource("/dashboard/dialogs/scannedPerson/scannedPersonDetails.fxml")));
        } catch (IOException e) {
            Logger.getLogger(ScannedPersonDialog.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
            e.printStackTrace();
        }
        getDialogPane().getScene().getWindow().sizeToScene();
    }

    private void initScanRequest() {
        setTitle("Warte auf Scanvorgang");
        setContentText("Bitte scannen Sie eine MultiCa ein um fortzufahren.");

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());

        getDialogPane().setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
                scannedPerson = PersonData.getPersons().get(random.nextInt(PersonData.getPersons().size()));
                initScanned();
            }
        });
    }

    private void initScanned() {
        setTitle("MultiCa wird gescannt...");
        setHeaderText("MultiCa wird gescannt...");
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
            ScannedPersonDetailsFXMLController.setCurrentPerson(scannedPerson);
            setDetailViewFXMLChild();

            setTitle("Eingescannte MultiCa");
            setHeaderText(null);
            setContentText(null);

            getDialogPane().getButtonTypes().clear();
            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            getDialogPane().getButtonTypes().add(ButtonType.APPLY);

            Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);

            Button applyButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
            if (transponder != null) {
                applyButton.setText("Verleihen");
                applyButton.setOnAction(e -> lendTransponder());
            } else {
                applyButton.setText("Transponder auswählen");
                applyButton.setOnAction(e -> chooseTransponder());
            }

            getDialogPane().getScene().getWindow().sizeToScene();
        });
        new Thread(sleeper).start();
    }

    private void chooseTransponder() {
        TranspondersFXMLController.CHOOSE_MODE_PERSON = scannedPerson;
        DashboardFXMLController.setDashboardFXMLChild("/dashboard/transponders/transponders.fxml");
    }

    private void lendTransponder() {
        List<Room> authorizationRooms = scannedPerson.authorizationsProperty().stream()
                .map(authorization -> authorization.roomProperty().getValue())
                .collect(Collectors.toList());

        boolean authorized = transponder.linkingsProperty().stream()
                .anyMatch(linking -> authorizationRooms.contains(linking.roomProperty().getValue()));

        Dialog next;
        if (authorized) {
            next = new LendingConfirmationDialog(scannedPerson, transponder);
        } else {
            next = new Dialog();
            next.setTitle("Unberechtigte Person");
            next.setHeaderText("Verleih nicht möglich");
            next.setContentText(scannedPerson.toString() + " ist nicht berechtigt den Transponder " + transponder.nameProperty().getValue() + " auszuleihen.");

            next.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

            Node closeButton = next.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
        }
        next.showAndWait();
    }
}

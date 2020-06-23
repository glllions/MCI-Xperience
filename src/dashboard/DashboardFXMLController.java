package dashboard;

import application.MainFXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.logging.Logger;

public class DashboardFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private StackPane stackPaneContent;

    @FXML
    private VBox vBoxNavigator;

    @FXML
    private VBox vBoxNavigatorComplete;

    @FXML
    private TextField textFieldSearch;

    private static StackPane staticStackPaneContent;

    static void setDashboardFXMLChild(String path) {
        try {
            staticStackPaneContent.getChildren().clear();
            staticStackPaneContent.getChildren().add(FXMLLoader.load(DashboardFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(DashboardFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
        }
    }

    @FXML
    private void initialize() {
        staticStackPaneContent = stackPaneContent;
        switchNavigator();
    }

    @FXML
    private void handleButtonNavigator(ActionEvent event) {
        switchNavigator();
    }

    @FXML
    private void handleButtonBack(ActionEvent event) {
    }

    @FXML
    private void handleButtonPersons(ActionEvent event) {
        setDashboardFXMLChild("/dashboard/persons/persons.fxml");
    }

    @FXML
    private void handleButtonRooms(ActionEvent event) {
        setDashboardFXMLChild("/dashboard/rooms/rooms.fxml");
    }

    @FXML
    private void handleButtonTransponders(ActionEvent event) {
        setDashboardFXMLChild("/dashboard/transponders/transponders.fxml");
    }

    private void switchNavigator() {
        if (vBoxNavigatorComplete.getChildren().contains(vBoxNavigator)) {
            vBoxNavigatorComplete.getChildren().remove(vBoxNavigator);
            vBoxNavigatorComplete.setBorder(null);
        } else {
            vBoxNavigatorComplete.getChildren().add(vBoxNavigator);
            vBoxNavigatorComplete.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }
}

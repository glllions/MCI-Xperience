package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Logger;

public class MainFXMLController {

    @FXML
    private StackPane root;

    private static StackPane staticRoot;

    public static void setMainFXMLChild(String path) {
        try {
            staticRoot.getChildren().clear();
            staticRoot.getChildren().add(FXMLLoader.load(MainFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(MainFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
        }
    }

    @FXML
    private void initialize() {
        staticRoot = root;
    }
}

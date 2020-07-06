package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import records.generator.DataGenerator;


public class Main extends Application {

    DataGenerator dataGenerator = new DataGenerator();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        MainFXMLController.setMainFXMLChild("/login/login.fxml");
        primaryStage.setTitle("Tokensystem");
        primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

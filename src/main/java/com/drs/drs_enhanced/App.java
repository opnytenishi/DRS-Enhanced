package com.drs.drs_enhanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass()
                .getResource("login_and_signup.fxml"));
        stage.setTitle("Disaster Response System");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void switchScene(Parent root) throws Exception {
        primaryStage.getScene().setRoot(root);
    }
    
    public static void main(String[] args) {
        launch();
    }

}
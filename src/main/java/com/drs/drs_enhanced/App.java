package com.drs.drs_enhanced;

import com.drs.drs_enhanced.backend.JPAUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        if (JPAUtil.checkConnection()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "DRS Database Connected Successfully :)", 
                    ButtonType.OK);
            alert.setTitle("Connection Status");
            alert.setHeaderText(null);
            alert.showAndWait(); 
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, 
                    "Failed to connect to the database : / \nRunning in test mode.", 
                    ButtonType.OK);
            alert.setTitle("Connection Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        Parent root = FXMLLoader.load(getClass().getResource("login_and_signup.fxml")); 
        Scene scene = new Scene(root);
        stage.setTitle("Disaster Response System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
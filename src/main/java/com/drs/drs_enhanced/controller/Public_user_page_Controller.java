/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.drs.drs_enhanced.controller;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Mohamed Badurudeen Tharick
 */
public class Public_user_page_Controller implements Initializable {

    @FXML
    private Text alert_safe_danger;

    @FXML
    private ComboBox<String> public_user_incident_type;

    @FXML
    private TextArea public_user_description;

    @FXML
    private Button public_user_request_help;

    @FXML
    private ListView<String> shelter_list;

    @FXML
    private Button public_user_logout_button;

    @FXML
    private ListView<String> public_notifications_list;

    @FXML
    private Text public_user_status_message_text_field;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Incident types dropdown
        public_user_status_message_text_field.setText("");

        ObservableList<String> incidentTypes = FXCollections.observableArrayList(
                "Flood",
                "Earthquake",
                "Fire",
                "Cyclone",
                "Medical Emergency",
                "Missing Person",
                "Accident",
                "Landslide"
        );
        public_user_incident_type.setItems(incidentTypes);

        // Fake shelter list
        ObservableList<String> shelters = FXCollections.observableArrayList(
                "Shelter A - North High School Gym",
                "Shelter B - Central Community Center",
                "Shelter C - South Park Hall",
                "Shelter D - East Emergency Zone",
                "Shelter E - West Town Stadium"
        );
        shelter_list.setItems(shelters);

        // Default notifications
        ObservableList<String> default_notification = FXCollections.observableArrayList(
                "Flood warning in Region A",
                "Power outage expected in Region B",
                "Emergency shelter open in Region C"
        );
        public_notifications_list.setItems(default_notification);
    }

    @FXML
    private void handleRequestHelpButtonClick() {
        String selectedIncident = public_user_incident_type.getValue();
        String description = public_user_description.getText().trim();

        if (selectedIncident == null || selectedIncident.isEmpty() || description.isEmpty()) {
            public_user_status_message_text_field.setFill(Color.RED);
            public_user_status_message_text_field.setText("Please select an incident type and enter a description.");
        } else {
            // Simulate request being sent
            public_user_status_message_text_field.setFill(Color.GREEN);
            public_user_status_message_text_field.setText("Request sent successfully! (Re-requesting help disabled for 2 hours)");

            // Disable inputs
            public_user_incident_type.setDisable(true);
            public_user_description.setDisable(true);
            public_user_request_help.setDisable(true);

            // Timer to re-enable inputs (2 hours = 7200 seconds)
            PauseTransition enableInputsTimer = new PauseTransition(Duration.seconds(30));  
            enableInputsTimer.setOnFinished(event -> {
                public_user_incident_type.setDisable(false);
                public_user_description.setDisable(false);
                public_user_request_help.setDisable(false);
                public_user_status_message_text_field.setFill(Color.DARKBLUE);
                public_user_status_message_text_field.setText("You can now submit another request if you did not received the help.");
            });

            enableInputsTimer.play();
        }
    }

    @FXML
    private void handleFakeLogoutFrom_public_user(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_and_signup.fxml"));
        Parent root = loader.load();

        // Get current stage
        Stage stage = (Stage) public_user_logout_button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("DRS System");
        stage.setResizable(false);
        stage.show();
    }
}

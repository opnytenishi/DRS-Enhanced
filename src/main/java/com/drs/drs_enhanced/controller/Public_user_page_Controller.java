package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.backend.ClientSocketHelper;
import com.drs.drs_enhanced.model.Incident;
import com.drs.drs_enhanced.model.Notification;
import com.drs.drs_enhanced.model.Shelter;
import com.drs.drs_enhanced.model.User;
import com.drs.drs_enhanced.view.IPublicUser;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Public_user_page_Controller implements Initializable, IPublicUser {

    @FXML
    private Text alert_safe_danger;
    @FXML
    private ComboBox<IncidentType> public_user_incident_type;
    @FXML
    private TextArea public_user_description;
    @FXML
    private Button public_user_request_help;
    @FXML
    private ListView<Shelter> shelter_list;
    @FXML
    private ListView<Notification> public_notifications_list;
    @FXML
    private Text public_user_status_message_text_field;

    private User loggedInUser;

    /**
     * Sets the logged-in user for the current controller context.
     *
     * @param loggedInUser the authenticated user object injected from the login
     */
    @Override
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        if (this.loggedInUser != null) {
            loadShelters();
            loadAlertByRegion();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        public_user_status_message_text_field.setText("");
        public_user_incident_type.getItems().setAll(IncidentType.values());
        loadNotifications();
    }

    /**
     * Resets all fields.
     */
    @Override
    public void resetFields() {
        public_user_description.setText("");
        public_user_incident_type.getSelectionModel().clearSelection();
        public_user_incident_type.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(IncidentType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Incident Type" : item.toString());
            }
        });
    }

    /**
     * Loads all shelters related to the currently logged-in user's region.
     */
    private void loadShelters() {
        Object response = ClientSocketHelper.sendRequest("getAllShelters", loggedInUser.getRegion());

        List<Shelter> shelters = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            for (Object obj : rawList) {
                if (obj instanceof Shelter) {
                    shelters.add((Shelter) obj);
                }
            }
        }

        ObservableList<Shelter> shelterList = FXCollections.observableArrayList(shelters);
        shelter_list.setItems(shelterList);
    }

    /**
     * Loads all notifications published.
     */
    private void loadNotifications() {
        Object response = ClientSocketHelper.sendRequest("getAllNotifications", null);

        List<Notification> notifications = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            for (Object obj : rawList) {
                if (obj instanceof Notification) {
                    notifications.add((Notification) obj);
                }
            }
        }

        ObservableList<Notification> notificationList = FXCollections.observableArrayList(notifications);
        public_notifications_list.setItems(notificationList);
    }

    /**
     * Loads all alerts related to the currently logged-in user's region.
     */
    private void loadAlertByRegion() {
        Object response = ClientSocketHelper.sendRequest("regionAlert", loggedInUser.getRegion());
        if (response instanceof Boolean) {
            boolean alert = (Boolean) response;
            if (alert) {
                alert_safe_danger.setFill(Color.RED);
                alert_safe_danger.setText("Your in Danger Zone!");
            } else {
                alert_safe_danger.setFill(Color.GREEN);
                alert_safe_danger.setText("Your in Safe Zone!");
            }
        }
    }

    /**
     * Handles the request submission by a public user Disables re-submission
     * for a period.
     */
    @FXML
    @Override
    public void handleRequestHelpButtonClick() {
        IncidentType selectedIncident = public_user_incident_type.getValue();
        String description = public_user_description.getText().trim();

        if (selectedIncident == null || description.isEmpty()) {
            public_user_status_message_text_field.setFill(Color.RED);
            public_user_status_message_text_field.setText("Please fill all details");
        } else {

            Incident incident = new Incident(selectedIncident.toString(),
                    description, selectedIncident.getPriority(),
                    loggedInUser, null);
            Object response = ClientSocketHelper.sendRequest("sendHelp", incident);

            if (response instanceof Boolean) {
                boolean success = (Boolean) response;
                if (success) {
                    public_user_status_message_text_field.setFill(Color.GREEN);
                    public_user_status_message_text_field.setText("Sent successfully! (Re-requesting disabled for 2 hr)");
                } else {
                    public_user_status_message_text_field.setFill(Color.RED);
                    public_user_status_message_text_field.setText("Submit Failed. Please try again");
                }
            } else {
                public_user_status_message_text_field.setFill(Color.RED);
                public_user_status_message_text_field.setText("Submit Failed. Please try again");
            }
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
                resetFields();
                public_user_status_message_text_field.setFill(Color.DARKBLUE);
                public_user_status_message_text_field.setText("You can now submit another request");
            });

            enableInputsTimer.play();
        }
    }

    /**
     * Reloads all data from db for the logged-in public user.
     */
    @FXML
    @Override
    public void handleReload() {
        loadShelters();
        loadAlertByRegion();
        loadNotifications();
    }

    /**
     * Logs out the current public user and redirects to the login screen.
     */
    @FXML
    @Override
    public void handleLogoutFrom_public_user() {
        try {
            setLoggedInUser(null);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/drs/drs_enhanced/login_and_signup.fxml"));
            Parent root = loader.load();
            App.switchScene(root);
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }
}

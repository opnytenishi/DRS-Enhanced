package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.backend.ClientSocketHelper;
import com.drs.drs_enhanced.model.Incident;
import com.drs.drs_enhanced.model.Supply;
import com.drs.drs_enhanced.model.User;
import com.drs.drs_enhanced.view.IOtherDepartment;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class Other_Department_PageController implements Initializable, IOtherDepartment {

    @FXML
    private Text department_name;
    @FXML
    private Text department_status_message;
    @FXML
    private TextArea assigned_task_textarea;
    @FXML
    private TextArea incident_details_textarea;
    @FXML
    private TextArea supplies_details_textarea;
    @FXML
    private Button previous_button;
    @FXML
    private Button next_button;

    private User loggedInUser;

    private List<Incident> assignedIncidents = new ArrayList<>();
    private int currentIncidentIndex = 0;

    @Override
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        if (this.loggedInUser != null) {
            loadAssignedIncidents();
            displaySupplies();
            
            this.department_name.setText(this.loggedInUser.getName() + " Dashboard");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department_status_message.setText("");

    }

    private void loadAssignedIncidents() {
        Object response = ClientSocketHelper.sendRequest("getIncidentsForDepartment", loggedInUser.getUserId());

        if (response instanceof List<?>) {
            assignedIncidents.clear();

            for (Object obj : (List<?>) response) {
                if (obj instanceof Incident) {
                    assignedIncidents.add((Incident) obj);
                }
            }

            currentIncidentIndex = 0;
            displayIncident(currentIncidentIndex);
        }

    }

    private void displaySupplies() {
        Object response = ClientSocketHelper.sendRequest("getSuppliesForDepartment", loggedInUser.getUserId());

        if (response instanceof List<?>) {
            String supplies = "";
            for (Object obj : (List<?>) response) {
                if (obj instanceof Supply) {
                    supplies += ((Supply) obj).getName() + "\n";
                }
            }

            supplies_details_textarea.setText(supplies);
        }
    }

    private void displayIncident(int index) {
        if (assignedIncidents.isEmpty()) {
            return;
        }

        Incident incident = assignedIncidents.get(index);

        assigned_task_textarea.setText(incident.getIncidentType());
        incident_details_textarea.setText(incident.getDescription());
        previous_button.setDisable(currentIncidentIndex == 0);
        next_button.setDisable(currentIncidentIndex == assignedIncidents.size() - 1);
    }

    @FXML
    @Override
    public void handleNextIncident() {
        if (currentIncidentIndex < assignedIncidents.size() - 1) {
            currentIncidentIndex++;
            displayIncident(currentIncidentIndex);
        }
    }

    @FXML
    @Override
    public void handlePreviousIncident() {
        if (currentIncidentIndex > 0) {
            currentIncidentIndex--;
            displayIncident(currentIncidentIndex);
        }
    }

    @FXML
    @Override
    public void handleMarkAsCompleted() {
        department_status_message.setText("✔ Marked as Completed. Thank you!");

        // Clear all fields after marking as completed
        assigned_task_textarea.clear();
        incident_details_textarea.clear();
        supplies_details_textarea.clear();
    }

    @FXML
    @Override
    public void handleLogoutFrom_department() {
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

    @FXML
    @Override
    public void handleReload() {
        if (this.loggedInUser != null) {
            loadAssignedIncidents();
            displaySupplies();
        }
    }
}

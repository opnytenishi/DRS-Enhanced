package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.backend.ClientSocketHelper;
import com.drs.drs_enhanced.model.Department;
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
    private Button department_logout_button;
    @FXML
    private Text department_status_message;
    @FXML
    private TextArea assigned_task_textarea;
    @FXML
    private TextArea incident_details_textarea;
    @FXML
    private TextArea supplies_details_textarea;
    @FXML
    private Button mark_as_completed_button;
    
    private User loggedInUser;
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        if (this.loggedInUser != null) {
            loadAssignedIncidents();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department_status_message.setText("");
  
    }

    private void loadAssignedIncidents() {
        Long deptId = ((Department) loggedInUser).getUserId();
        Object response = ClientSocketHelper.sendRequest("getIncidentsForDepartment", deptId);

        if (response instanceof List<?>) {
            List<Incident> incidents = new ArrayList<>();

            for (Object obj : (List<?>) response) {
                if (obj instanceof Incident) {
                    incidents.add((Incident) obj);
                }
            }

            if (!incidents.isEmpty()) {
                Incident firstIncident = incidents.get(0);
                assigned_task_textarea.setText(firstIncident.getIncidentType());
                incident_details_textarea.setText(firstIncident.getDescription());

                List<Supply> supplies = ((Department) loggedInUser).getSupplies();
                supplies_details_textarea.setText(supplies.toString());
            }
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
        // Add the load handlers here.
    }
}

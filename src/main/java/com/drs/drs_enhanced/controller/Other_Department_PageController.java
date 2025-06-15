package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.view.IOtherDepartment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department_status_message.setText("");

        // Load some fake data
        assigned_task_textarea.setText("Assigned Task: Rescue Operation in East Region.\nTeam: Rescue Squad 7.");
        incident_details_textarea.setText("Incident Details:\n- Flooding due to heavy rain\n- People stranded in buildings\n- Power lines down");
        supplies_details_textarea.setText("Supplies:\n- 10 Life Jackets\n- 5 Rescue Boats\n- 20 First Aid Kits");
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
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/drs/drs_enhanced/login_and_signup.fxml"));
            Parent root = loader.load();
            App.switchScene(root);
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }
}

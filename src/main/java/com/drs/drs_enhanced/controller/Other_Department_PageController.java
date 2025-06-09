/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.drs.drs_enhanced.controller;

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
/**
 * FXML Controller class
 *
 * @author Mohamed Badurudeen Tharick
 */
public class Other_Department_PageController implements Initializable {

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
    private void handleMarkAsCompleted() {
        department_status_message.setText("✔ Marked as Completed. Thank you!");

        // Clear all fields after marking as completed
        assigned_task_textarea.clear();
        incident_details_textarea.clear();
        supplies_details_textarea.clear();
    }

    @FXML
    private void handleFakeLogoutFrom_department(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/drs/drs_enhanced/login_and_signup.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) department_logout_button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("DRS System");
        stage.setResizable(false);
        stage.show();
    }
}

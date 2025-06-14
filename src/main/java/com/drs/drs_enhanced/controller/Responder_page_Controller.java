/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.view.IResponder;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Badurudeen Tharick
 */
public class Responder_page_Controller implements Initializable, IResponder {

    @FXML
    private Button responder_logout_button;
    @FXML
    private Text success_or_error_status;

    @FXML
    private ListView<String> incidentList_for_assign_team;
    @FXML
    private ComboBox<String> assign_teamComboBox;
    @FXML
    private Button assign_team_proceedButton;

    @FXML
    private ListView<String> activeTeamList_for_supplies;

    @FXML
    private Button add_new_supplies;
    @FXML
    private TextField new_supplies_name;
    @FXML
    private ComboBox<String> select_supplies_list_combobox;
    @FXML
    private Button assign_supplies_proceed_button;

    @FXML
    private TextArea shelter_details_from_responder_textbox;
    @FXML
    private ComboBox<String> select_shelter_region_from_responder_combobox;
    @FXML
    private Button send_nearby_shelter_by_responder_button;

    @FXML
    private TextArea notification_by_responder_textbox;
    @FXML
    private Button send_notification_by_responder_button;

    @FXML
    private ComboBox<String> select_region_for_alerting_combobox;
    @FXML
    private Button send_alert_to_selected_region_button;
    @FXML
    private ComboBox<String> remove_selected_region_from_alerting_combobox;
    @FXML
    private Button remove_alert_selected_region_button;

    @FXML
    private TabPane tabPane;

    private final String[] fakeIncidents = {
        "Flood in North \n Priority 1", "Fire in East \n Priority 1", "Medical Emergency in South \n Priority 3"
    };

    private final String[] fakeTeams = {
        "Team Alpha", "Team Bravo", "Team Charlie"
    };

    private final String[] regions = {
        "NORTH", "SOUTH", "EAST", "WEST", "CENTRAL"
    };

    private final Set<String> supplyNamesSet = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        success_or_error_status.setText("");

        // Populate fake incidents into ListView
        incidentList_for_assign_team.getItems().addAll(fakeIncidents);

        // Populate teams into ComboBox and ListView
        for (String team : fakeTeams) {
            assign_teamComboBox.getItems().add(team);
            activeTeamList_for_supplies.getItems().add(team);
        }

        // Populate regions into ComboBoxes
        select_region_for_alerting_combobox.getItems().addAll(regions);
        select_shelter_region_from_responder_combobox.getItems().addAll(regions);
        remove_selected_region_from_alerting_combobox.getItems().addAll(regions);

        for (Tab tab : tabPane.getTabs()) {
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    resetFields();  // Reset inputs when a tab is selected
                }
            });
        }

    }

    @Override
    public void resetFields() {
        success_or_error_status.setText("");
        new_supplies_name.clear();
        shelter_details_from_responder_textbox.clear();
        notification_by_responder_textbox.clear();
    }

    @FXML
    @Override
    public void handleLogoutFrom_responder() {
        try {
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
    public void handleAssignTeamToIncident() {
        String selectedTeam = assign_teamComboBox.getValue();
        if (selectedTeam != null) {
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("✔ " + selectedTeam + " assigned to the incident successfully.");
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please select a team to assign.");
        }
    }

    @FXML
    @Override
    public void handleAddNewSupply() {
        String supplyName = new_supplies_name.getText().trim();

        if (supplyName.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please enter supplies details.");
            return;
        }

        String fullSupply = supplyName;

        if (!supplyNamesSet.contains(fullSupply)) {
            select_supplies_list_combobox.getItems().add(fullSupply);
            supplyNamesSet.add(fullSupply);
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("✔ Supply added successfully.");
        } else {
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("⚠ Supply already exists.");
        }

        new_supplies_name.clear();
    }

    @FXML
    @Override
    public void handleSendNearbyShelter() {
        String details = shelter_details_from_responder_textbox.getText().trim();
        String region = select_shelter_region_from_responder_combobox.getValue();

        if (details.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please enter shelter details.");
            return;
        }

        if (region == null || region.trim().isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please select a region.");
            return;
        }
        success_or_error_status.setFill(Color.GREEN);
        success_or_error_status.setText("✔ Shelter info sent for " + region + " region.");

        shelter_details_from_responder_textbox.clear();
    }

    @FXML
    @Override
    public void handleSendNotification() {
        String notification = notification_by_responder_textbox.getText().trim();

        if (notification.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please enter notification message");
            return;
        }
        success_or_error_status.setFill(Color.GREEN);
        success_or_error_status.setText("✔ Notification sent");

        notification_by_responder_textbox.clear();
    }

    @FXML
    @Override
    public void handleSendAlertToRegion() {
        String selectedRegion = select_region_for_alerting_combobox.getValue();
        if (selectedRegion != null) {
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("✔ Alert sent to " + selectedRegion + " region.");
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please select a region to alert.");
        }
    }

    @FXML
    @Override
    public void handleRemoveAlertFromRegion() {
        String selectedRegion = remove_selected_region_from_alerting_combobox.getValue();
        if (selectedRegion != null) {
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("✔ Alert removed from " + selectedRegion + " region.");
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please select a region to remove alert.");
        }
    }

    @FXML
    @Override
    public void handleassign_suppliessuccess() {
        success_or_error_status.setFill(Color.GREEN);
        success_or_error_status.setText("✔ Supplies assigned successfully.");
    }
}

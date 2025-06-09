/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.drs.drs_enhanced.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Badurudeen Tharick
 */
public class Responder_page_Controller implements Initializable {

    @FXML private Button responder_logout_button;
    @FXML private Text success_or_error_status;

    @FXML private ListView<String> incidentList_for_assign_team;
    @FXML private ComboBox<String> assign_teamComboBox;
    @FXML private Button assign_team_proceedButton;

    @FXML private ListView<String> activeTeamList_for_supplies;

    @FXML private Button add_new_supplies;
    @FXML private TextField new_supplies_name;
    @FXML private TextField new_supplies_quantity;
    @FXML private ComboBox<String> select_supplies_list_combobox;
    @FXML private Button assign_supplies_proceed_button;

    @FXML private ComboBox<String> select_region_for_alerting_combobox;
    @FXML private Button send_alert_to_selected_region_button;
    @FXML private ComboBox<String> remove_selected_region_from_alerting_combobox;
    @FXML private Button remove_alert_selected_region_button;

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
        remove_selected_region_from_alerting_combobox.getItems().addAll(regions);
    }

    @FXML
    private void handleFakeLogoutFrom_responder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_and_signup.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) responder_logout_button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("DRS System");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleAssignTeamToIncident(ActionEvent event) {
        String selectedTeam = assign_teamComboBox.getValue();
        if (selectedTeam != null) {
            success_or_error_status.setText("✔ " + selectedTeam + " assigned to the incident successfully.");
        } else {
            success_or_error_status.setText("⚠ Please select a team to assign.");
        }
    }

    @FXML
    private void handleAddNewSupply(ActionEvent event) {
        String supplyName = new_supplies_name.getText().trim();
        String quantityText = new_supplies_quantity.getText().trim();

        if (supplyName.isEmpty() || quantityText.isEmpty()) {
            success_or_error_status.setText("⚠ Please enter both supply name and quantity.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            String fullSupply = supplyName + " x" + quantity;

            if (!supplyNamesSet.contains(fullSupply)) {
                select_supplies_list_combobox.getItems().add(fullSupply);
                supplyNamesSet.add(fullSupply);
                success_or_error_status.setText("✔ Supply added successfully.");
            } else {
                success_or_error_status.setText("⚠ Supply already exists.");
            }

            new_supplies_name.clear();
            new_supplies_quantity.clear();

        } catch (NumberFormatException e) {
            success_or_error_status.setText("⚠ Quantity must be a number.");
        }
    }

    @FXML
    private void handleSendAlertToRegion(ActionEvent event) {
        String selectedRegion = select_region_for_alerting_combobox.getValue();
        if (selectedRegion != null) {
            success_or_error_status.setText("✔ Alert sent to " + selectedRegion + " region.");
        } else {
            success_or_error_status.setText("⚠ Please select a region to alert.");
        }
    }

    @FXML
    private void handleRemoveAlertFromRegion(ActionEvent event) {
        String selectedRegion = remove_selected_region_from_alerting_combobox.getValue();
        if (selectedRegion != null) {
            success_or_error_status.setText("✔ Alert removed from " + selectedRegion + " region.");
        } else {
            success_or_error_status.setText("⚠ Please select a region to remove alert.");
        }
    }

    @FXML
    private void handleassign_suppliessuccess(ActionEvent event) {
        success_or_error_status.setText("✔ Supplies assigned successfully.");
    }
}
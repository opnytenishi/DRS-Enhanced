package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.backend.ClientSocketHelper;
import com.drs.drs_enhanced.model.Alert;
import com.drs.drs_enhanced.model.Department;
import com.drs.drs_enhanced.model.Incident;
import com.drs.drs_enhanced.model.Notification;
import com.drs.drs_enhanced.model.Shelter;
import com.drs.drs_enhanced.model.Supply;
import com.drs.drs_enhanced.view.IResponder;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Responder_page_Controller implements Initializable, IResponder {

    @FXML
    private Text success_or_error_status;
    @FXML
    private ListView<Incident> incidentList_for_assign_team;
    @FXML
    private ComboBox<Department> assign_teamComboBox;
    @FXML
    private ListView<Department> activeTeamList_for_supplies;
    @FXML
    private TextField new_supplies_name;
    @FXML
    private ComboBox<Supply> select_supplies_list_combobox;
    @FXML
    private TextArea shelter_details_from_responder_textbox;
    @FXML
    private ComboBox<Region> select_shelter_region_from_responder_combobox;
    @FXML
    private TextArea notification_by_responder_textbox;
    @FXML
    private ComboBox<Region> select_region_for_alerting_combobox;
    @FXML
    private ComboBox<Alert> remove_selected_region_from_alerting_combobox;
    @FXML
    private TabPane tabPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        success_or_error_status.setText("");

        loadIncidents();
        loadDepartments();
        loadSupplies();
        loadAlerts();

        select_region_for_alerting_combobox.getItems().addAll(Region.values());
        select_shelter_region_from_responder_combobox.getItems().addAll(Region.values());

        for (Tab tab : tabPane.getTabs()) {
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    resetFields();
                }
            });
        }

    }

    /**
     * Loads all incidents that are not assigned to any departments.
     */
    private void loadIncidents() {
        Object response = ClientSocketHelper.sendRequest("getUnassignedIncidents", null);
        List<Incident> incidents = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            if (!rawList.isEmpty() && rawList.get(0) instanceof Incident) {
                for (Object obj : rawList) {
                    incidents.add((Incident) obj);
                }
            }
        }

        // priority based sorting 
        incidents.sort(Comparator.comparingInt(Incident::getPriorityLevel));

        ObservableList<Incident> observableIncidents = FXCollections.observableArrayList(incidents);
        incidentList_for_assign_team.setItems(observableIncidents);
        incidentList_for_assign_team.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Incident incident, boolean empty) {
                super.updateItem(incident, empty);
                if (empty || incident == null) {
                    setText(null);
                } else {
                    setText(incident.getIncidentType() + " in " + incident.getUser().getRegion()
                            + "\nPriority " + incident.getPriorityLevel());
                }
            }
        });
    }

    /**
     * Loads all departments added.
     */
    private void loadDepartments() {
        Object response = ClientSocketHelper.sendRequest("getAllDepartments", null);

        List<Department> departments = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            for (Object obj : rawList) {
                if (obj instanceof Department) {
                    departments.add((Department) obj);
                }
            }
        }

        ObservableList<Department> departmentList = FXCollections.observableArrayList(departments);
        assign_teamComboBox.setItems(departmentList);
        activeTeamList_for_supplies.setItems(departmentList);
        activeTeamList_for_supplies.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Department dept, boolean empty) {
                super.updateItem(dept, empty);
                if (empty || dept == null) {
                    setText(null);
                } else {
                    StringBuilder sb = new StringBuilder(dept.getDepartmentName());

                    if (dept.getSupplies() != null && !dept.getSupplies().isEmpty()) {
                        sb.append(" (");
                        sb.append(
                                dept.getSupplies().stream()
                                        .map(Supply::getName)
                                        .collect(Collectors.joining(", "))
                        );
                        sb.append(")");
                    }

                    setText(sb.toString());
                }
            }
        });

    }

    /**
     * Loads all supplies created.
     */
    private void loadSupplies() {
        Object response = ClientSocketHelper.sendRequest("getAllSupplies", null);

        List<Supply> supplies = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            for (Object obj : rawList) {
                if (obj instanceof Supply) {
                    supplies.add((Supply) obj);
                }
            }
        }

        ObservableList<Supply> suppliesList = FXCollections.observableArrayList(supplies);
        select_supplies_list_combobox.setItems(suppliesList);
    }

    /**
     * Loads all alerts created.
     */
    private void loadAlerts() {
        Object response = ClientSocketHelper.sendRequest("getAllAlerts", null);

        List<Alert> alerts = new ArrayList<>();
        if (response instanceof List<?>) {
            List<?> rawList = (List<?>) response;
            for (Object obj : rawList) {
                if (obj instanceof Alert) {
                    alerts.add((Alert) obj);
                }
            }
        }

        ObservableList<Alert> alertList = FXCollections.observableArrayList(alerts);
        remove_selected_region_from_alerting_combobox.setItems(alertList);
    }

    /**
     * Resets all fields.
     */
    @Override
    public void resetFields() {
        success_or_error_status.setText("");
        new_supplies_name.clear();
        shelter_details_from_responder_textbox.clear();
        notification_by_responder_textbox.clear();

        assign_teamComboBox.getSelectionModel().clearSelection();
        assign_teamComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select team" : item.toString());
            }
        });

        select_supplies_list_combobox.getSelectionModel().clearSelection();
        select_supplies_list_combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Supply item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Assign Supplies" : item.toString());
            }
        });

        select_shelter_region_from_responder_combobox.getSelectionModel().clearSelection();
        select_shelter_region_from_responder_combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Region item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select the Region" : item.toString());
            }
        });

        select_region_for_alerting_combobox.getSelectionModel().clearSelection();
        select_region_for_alerting_combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Region item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select the Region to send alert" : item.toString());
            }
        });

        remove_selected_region_from_alerting_combobox.getSelectionModel().clearSelection();
        remove_selected_region_from_alerting_combobox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Alert item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select the Region to remove from Alert mode" : item.toString());
            }
        });
    }

    /**
     * Logs out the current responder user and redirects to the login screen.
     */
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

    /**
     * Handles the process of assigning a department to reported incident.
     */
    @FXML
    @Override
    public void handleAssignTeamToIncident() {

        Incident selectedIncident = incidentList_for_assign_team.getSelectionModel().getSelectedItem();
        Department selectedTeam = assign_teamComboBox.getValue();

        if (selectedIncident == null || selectedTeam == null) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please select both an incident and a team.");
            return;
        }

        selectedIncident.setAssignedDepartment(selectedTeam);

        Object response = ClientSocketHelper.sendRequest("assignTeamToIncident", selectedIncident);
        if (response instanceof Boolean && (Boolean) response) {
            success_or_error_status.setFill(Color.GREEN);
            success_or_error_status.setText("✔ " + selectedTeam.getDepartmentName() + " assigned to the incident successfully.");
            loadIncidents();
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Assignment failed.");
        }

    }

    /**
     * Adds a new supply.
     */
    @FXML
    @Override
    public void handleAddNewSupply() {
        String supplyName = new_supplies_name.getText().trim();

        if (supplyName.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please enter supply name.");
            return;
        }

        Supply supply = new Supply(supplyName, null);
        Object response = ClientSocketHelper.sendRequest("addSupply", supply);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;
            if (success) {
                success_or_error_status.setFill(Color.GREEN);
                success_or_error_status.setText("✔ Supply added successfully.");
            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("⚠ Supply already exists.");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Submit Failed");
        }
        loadSupplies();
        new_supplies_name.clear();
    }

    /**
     * Handles the adding a nearby shelter for region.
     */
    @FXML
    @Override
    public void handleSendNearbyShelter() {
        String details = shelter_details_from_responder_textbox.getText().trim();
        Region region = select_shelter_region_from_responder_combobox.getValue();

        if (details.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please enter shelter details.");
            return;
        }

        if (region == null || region.toString().isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please select a region.");
            return;
        }
        Shelter shelter = new Shelter(details, region.toString());
        Object response = ClientSocketHelper.sendRequest("addShelter", shelter);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;
            if (success) {
                success_or_error_status.setFill(Color.GREEN);
                success_or_error_status.setText("✔ Shelter info sent for " + region + " region.");
            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("⚠ Shelter already exists.");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Submit Failed");
        }

        shelter_details_from_responder_textbox.clear();

    }

    /**
     * Creates Notifications.
     */
    @FXML
    @Override
    public void handleSendNotification() {
        String notification = notification_by_responder_textbox.getText().trim();

        if (notification.isEmpty()) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please enter notification message");
            return;
        }
        Notification notificationObject = new Notification(notification);
        Object response = ClientSocketHelper.sendRequest("addNotification", notificationObject);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;
            if (success) {
                success_or_error_status.setFill(Color.GREEN);
                success_or_error_status.setText("✔ Notification Sent");
            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("⚠ Notification Already exists.");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Notifcation Failed");
        }

        notification_by_responder_textbox.clear();
    }

    /**
     * Handles the process of creating alerts for regions.
     */
    @FXML
    @Override
    public void handleSendAlertToRegion() {

        Region selectedRegion = select_region_for_alerting_combobox.getValue();

        if (selectedRegion == null) {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please select a region to alert.");
            return;
        }

        Alert alertObject = new Alert(selectedRegion.toString());
        Object response = ClientSocketHelper.sendRequest("addAlert", alertObject);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;
            if (success) {
                success_or_error_status.setFill(Color.GREEN);
                success_or_error_status.setText("✔ Alert sent to " + selectedRegion + " region.");
                loadAlerts();
            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("⚠ " + selectedRegion + " region has been already alerted. select different region.");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Alert Failed");
        }
    }

    /**
     * Handles the process of deleting alerts for regions.
     */
    @FXML
    @Override
    public void handleRemoveAlertFromRegion() {
        Alert selectedRegion = remove_selected_region_from_alerting_combobox.getValue();
        if (selectedRegion != null) {
            Object response = ClientSocketHelper.sendRequest("deleteAlert", selectedRegion);
            if (response instanceof Boolean && (Boolean) response) {
                success_or_error_status.setFill(Color.GREEN);
                success_or_error_status.setText("✔ Alert removed from " + selectedRegion + " region.");
                loadAlerts();

            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("Alert failed to remove");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("⚠ Please select a region to remove alert.");
        }
    }

    /**
     * Handles the process of assigning a supplies to departments.
     */
    @FXML
    @Override
    public void handleassign_supplies() {
        Department dept = activeTeamList_for_supplies.getSelectionModel().getSelectedItem();
        Supply selectedSupply = select_supplies_list_combobox.getValue();

        if (dept != null && selectedSupply != null) {
            Map<String, Long> data = new HashMap<>();
            data.put("deptId", dept.getUserId());
            data.put("supplyId", selectedSupply.getId());
            Object response = ClientSocketHelper.sendRequest("assignSupplyToTeam", data);

            if (response instanceof Boolean) {
                boolean success = (Boolean) response;
                if (success) {
                    success_or_error_status.setFill(Color.GREEN);
                    success_or_error_status.setText("✔ Supply assigned successfully.");
                } else {
                    success_or_error_status.setFill(Color.RED);
                    success_or_error_status.setText("⚠ Supply Already Assigned.");
                }
            } else {
                success_or_error_status.setFill(Color.RED);
                success_or_error_status.setText("Assignment Failed.");
            }
        } else {
            success_or_error_status.setFill(Color.RED);
            success_or_error_status.setText("Please select both a team and a supply.");
        }

        loadDepartments();
    }

    /**
     * Reloads all data from db for the logged-in responder user.
     */
    @FXML
    @Override
    public void handleReload() {
        loadIncidents();
        loadDepartments();
        loadSupplies();
        loadAlerts();
        resetFields();
    }
}

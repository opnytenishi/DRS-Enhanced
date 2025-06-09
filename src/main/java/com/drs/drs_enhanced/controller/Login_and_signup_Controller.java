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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Login_and_signup_Controller class for login and Register user interface
 *
 * @author Mohamed Badurudeen Tharick
 */
public class Login_and_signup_Controller implements Initializable {

    @FXML
    private TextField login_email_or_phone_no;
    @FXML
    private ComboBox<UserType> login_user_type;
    @FXML
    private PasswordField login_password;
    @FXML
    private ComboBox<Region> register_user_region;
    @FXML
    private TextArea register_user_full_address;
    @FXML
    private TextField register_user_email_phone;
    @FXML
    private TextField register_user_name;
    @FXML
    private PasswordField register_user_password;
    @FXML
    private Button user_login_button;
    @FXML
    private Button register_public_user_button;
    @FXML
    private Text login_alert_message;
    @FXML
    private Text register_public_user_alert_message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
        login_user_type.getItems().setAll(UserType.values());
        register_user_region.getItems().setAll(Region.values());
        login_alert_message.setText("");        
        register_public_user_alert_message.setText("");


    }

    public enum UserType {
        PUBLIC_USER("Public User"),
        GUEST_USER("Guest User"),
        RESPONDER("Responder"),
        OTHER_DEPARTMENT("Fire Station"),        
        OTHER_DEPARTMENT_2("Mobile Hospital"),        
        OTHER_DEPARTMENT_3("Rescue Team");

        private final String displayName;

        UserType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }

    }

    public enum Region {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        CENTRAL
    }

    @FXML
    private void handleUserLogin(ActionEvent event) throws IOException {
        UserType selectedUserType = login_user_type.getValue();

        if (selectedUserType == null) {
            login_alert_message.setText("Please select a user type.");          
            return;
        }

        String fxmlFile = null;
        String title = null;

        switch (selectedUserType) {
            case PUBLIC_USER:
                fxmlFile = "public_user_page.fxml";
                title = "DRS - Public User";
                break;
            case RESPONDER:
                fxmlFile = "responder_page.fxml";
                title = "DRS - Responder";
                break;
            case OTHER_DEPARTMENT:
                fxmlFile = "other_department.fxml";
                title = "DRS - Other Department";
                break;
            case OTHER_DEPARTMENT_2:
                fxmlFile = "other_department.fxml";
                title = "DRS - Other Department";
                break;
            case OTHER_DEPARTMENT_3:
                fxmlFile = "other_department.fxml";
                title = "DRS - Other Department";
                break;
            case GUEST_USER:
                fxmlFile = "public_user_page.fxml";
                title = "DRS - Guest User";
                break;
        }

        if (fxmlFile != null) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/drs/drs_enhanced/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) user_login_button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        }
    }
    
    @FXML
    private void redirect_to_login_page(ActionEvent event) throws IOException {
            register_public_user_alert_message.setText("Registration is under development,\nPlease login from login tab, Thank You.");          
    }

}

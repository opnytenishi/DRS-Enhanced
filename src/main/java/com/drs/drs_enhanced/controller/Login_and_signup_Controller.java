package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.backend.ClientSocketHelper;
import static com.drs.drs_enhanced.controller.UserType.OTHER_DEPARTMENT;
import com.drs.drs_enhanced.model.*;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_and_signup_Controller implements Initializable {

    @FXML
    private TextField login_email;
    @FXML
    private ComboBox<UserType> login_user_type;
    @FXML
    private PasswordField login_password;
    @FXML
    private ComboBox<Region> register_user_region;
    @FXML
    private TextArea register_user_full_address;
    @FXML
    private TextField register_user_email;
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
    @FXML
    private TextField register_department_name;
    @FXML
    private TextField register_department_email;
    @FXML
    private TextField register_department_password;
    @FXML
    private Text department_register_alert_message;
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
        login_user_type.getItems().setAll(UserType.values());
        register_user_region.getItems().setAll(Region.values());
        login_alert_message.setText("");
        register_public_user_alert_message.setText("");
        department_register_alert_message.setText("");

        for (Tab tab : tabPane.getTabs()) {
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    resetFields();
                }
            });
        }

    }
    /**
     * Resets all text fields in the current tab.
     */
    public void resetFields() {
        login_email.clear();
        login_password.setText("");
        register_user_full_address.clear();
        register_user_email.clear();
        register_user_name.clear();
        register_user_password.clear();
        login_alert_message.setText("");
        register_public_user_alert_message.setText("");
        register_department_name.setText("");
        register_department_email.setText("");
        register_department_password.setText("");
        department_register_alert_message.setText("");
    }

    @FXML
    private void handleUserLogin(ActionEvent event) throws IOException {
        UserType selectedUserType = login_user_type.getValue();

        if (selectedUserType == null) {
            login_alert_message.setText("Please select a user type.");
            return;
        }
        if (login_email.getText().trim().isEmpty() || login_password.getText().trim().isEmpty()) {
            login_alert_message.setText("Please enter all details.");
            return;
        }

        String fxmlFile = null;
        String title = null;

        User loginUser;

        switch (selectedUserType) {
            case PUBLIC_USER:
                fxmlFile = "public_user_page.fxml";
                title = "DRS - Public User";
                loginUser = new PublicUser();
                break;
            case RESPONDER:
                fxmlFile = "responder_page.fxml";
                title = "DRS - Responder";
                loginUser = new User();
                break;
            case OTHER_DEPARTMENT:
                fxmlFile = "other_department.fxml";
                title = "DRS - Other Department";
                loginUser = new Department();
                break;
            case GUEST_USER:
                fxmlFile = "public_user_page.fxml";
                title = "DRS - Guest User";
                loginUser = new PublicUser();
                break;
            default:
                fxmlFile = "public_user_page.fxml";
                title = "DRS - Guest User";
                loginUser = new PublicUser();
                break;
        }

        loginUser.setEmail(login_email.getText().trim());
        loginUser.setPassword(login_password.getText().trim());

        Object response = ClientSocketHelper.sendRequest("login", loginUser);

        if (response instanceof User) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/drs/drs_enhanced/" + fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) user_login_button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } else {
            login_alert_message.setText("Login Failed. Please try again!");
        }

    }

    @FXML
    private void registerUser(ActionEvent event) throws IOException {
        String name = register_user_name.getText().trim();
        String email = register_user_email.getText().trim();
        String password = register_user_password.getText().trim();
        String address = register_user_full_address.getText().trim();
        Region region = register_user_region.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                || address.isEmpty() || region == null) {
            register_public_user_alert_message.setText("Please enter all details.");
            return;
        }

        User user = new PublicUser(name, email, password, address);

        Object response = ClientSocketHelper.sendRequest("register", user);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;

            if (success) {
                register_public_user_alert_message
                        .setText("User Registration,\nPlease login from login tab, Thank You.");
            } else {
                register_public_user_alert_message.setText("Registration Failed. Please try again!");
            }
        } else {
            register_public_user_alert_message.setText("Registration Failed. Please try again!");
        }
    }

    @FXML
    private void registerOtherDepartmentUser(ActionEvent event) throws IOException {
        String name = register_department_name.getText().trim();
        String email = register_department_email.getText().trim();
        String password = register_department_password.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            department_register_alert_message.setText("Please enter all details.");
            return;
        }

        User user = new Department(name, null, name, email, password, "NA");

        Object response = ClientSocketHelper.sendRequest("register", user);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;

            if (success) {
                department_register_alert_message
                        .setText("Department User Registration,\nPlease login from login tab, Thank You.");
            } else {
                department_register_alert_message.setText("Registration Failed. Please try again!");
            }
        } else {
            department_register_alert_message.setText("Registration Failed. Please try again!");
        }
    }

}

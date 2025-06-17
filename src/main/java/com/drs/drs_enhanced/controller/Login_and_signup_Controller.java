package com.drs.drs_enhanced.controller;

import com.drs.drs_enhanced.App;
import com.drs.drs_enhanced.backend.ClientSocketHelper;
import static com.drs.drs_enhanced.controller.UserType.OTHER_DEPARTMENT;
import com.drs.drs_enhanced.model.*;
import com.drs.drs_enhanced.view.ILoginAndSignup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Login_and_signup_Controller implements Initializable, ILoginAndSignup {

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
    @Override
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
        
        login_user_type.getSelectionModel().clearSelection();
        login_user_type.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UserType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select user type" : item.toString());
            }
        });
        
        register_user_region.getSelectionModel().clearSelection();
        register_user_region.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Region item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Select the Region" : item.toString());
            }
        });
    }

    @FXML
    @Override
    public void handleUserLogin() {
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

        User loginUser;

        switch (selectedUserType) {
            case PUBLIC_USER:
                fxmlFile = "public_user_page.fxml";
                loginUser = new PublicUser();
                break;
            case RESPONDER:
                fxmlFile = "responder_page.fxml";
                loginUser = new Responder();
                break;
            case OTHER_DEPARTMENT:
                fxmlFile = "other_department.fxml";
                loginUser = new Department();
                break;
            default:
                fxmlFile = "public_user_page.fxml";
                loginUser = new PublicUser();
                break;
        }

        loginUser.setEmail(login_email.getText().trim());
        loginUser.setPassword(login_password.getText().trim());

        Object response = ClientSocketHelper.sendRequest("login", loginUser);

        if (response instanceof User) {

            User loggedInUser = (User) response;

            boolean valid = false;

            switch (selectedUserType) {
                case PUBLIC_USER:
                    valid = loggedInUser instanceof PublicUser;
                    break;
                case RESPONDER:
                    valid = loggedInUser instanceof Responder;
                    break;
                case OTHER_DEPARTMENT:
                    valid = loggedInUser instanceof Department;
                    break;
                default:
                    valid = false;
            }

            if (!valid) {
                login_alert_message.setText("Incorrect user type selected.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/drs/drs_enhanced/" + fxmlFile));
                Parent root = loader.load();

                switch (selectedUserType) {
                    case PUBLIC_USER:
                        Public_user_page_Controller controller = loader.getController();
                        controller.setLoggedInUser(loggedInUser);
                        break;
                    case RESPONDER:
                        break;
                    case OTHER_DEPARTMENT:
                        Other_Department_PageController controller1 = loader.getController();
                        controller1.setLoggedInUser(loggedInUser);
                        break;
                    default:
                        break;
                }
                App.switchScene(root);

            } catch (Exception ex) {
                System.out.println("Error : " + ex.getMessage());
            }

        } else {
            login_alert_message.setText("Login Failed. Please try again!");
        }

    }

    @FXML
    @Override
    public void registerUser() {
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

        User user = new PublicUser(name, email, password, region.toString(), address);
        Object response = ClientSocketHelper.sendRequest("register", user);

        if (response instanceof Boolean) {
            boolean success = (Boolean) response;
            if (success) {
                register_public_user_alert_message.setFill(Color.GREEN);
                register_public_user_alert_message
                        .setText("Public User Registered,\nPlease login from login tab, Thank You.");

            } else {
                register_public_user_alert_message.setText("Registration Failed. Please try again!");
            }
        } else {
            register_public_user_alert_message.setText("Registration Failed. Please try again!");
        }
    }

    @FXML
    @Override
    public void registerOtherDepartmentUser() {
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
                department_register_alert_message.setFill(Color.GREEN);
                department_register_alert_message
                        .setText("Department User Registered,\nPlease login from login tab, Thank You.");
            } else {
                department_register_alert_message.setText("Registration Failed. Please try again!");
            }
        } else {
            department_register_alert_message.setText("Registration Failed. Please try again!");
        }
    }

}

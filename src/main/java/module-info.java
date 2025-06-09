module com.drs.drs_enhanced {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;

    opens com.drs.drs_enhanced to javafx.fxml;
    opens com.drs.drs_enhanced.controller to javafx.fxml;
    opens com.drs.drs_enhanced.model;
    exports com.drs.drs_enhanced;
}

module com.drs.drs_enhanced {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.drs.drs_enhanced to javafx.fxml;
    exports com.drs.drs_enhanced;
}

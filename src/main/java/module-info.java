module com.ppcc.PatientCareCenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.dlsc.formsfx;

    opens com.ppcc.PatientCareCenter to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Views to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers to javafx.fxml;
    exports com.ppcc.PatientCareCenter;
    exports com.ppcc.PatientCareCenter.Controllers;
    exports com.ppcc.PatientCareCenter.Controllers.Admin;
    exports com.ppcc.PatientCareCenter.Controllers.Client;
    exports com.ppcc.PatientCareCenter.Model;
    exports com.ppcc.PatientCareCenter.Views;
}
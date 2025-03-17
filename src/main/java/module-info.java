module com.ppcc.PatientCareCenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.dlsc.formsfx;
    requires org.slf4j;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;

    opens com.ppcc.PatientCareCenter to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Views to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Views.Panes to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Views.Stages to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Views.Components to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers.LoginOrSignup to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers.Account to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers.Client to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Controllers.Admin to javafx.fxml;
    opens com.ppcc.PatientCareCenter.Database.User to javafx.base;
    opens com.ppcc.PatientCareCenter.Database to javafx.base;
    exports com.ppcc.PatientCareCenter;
    exports com.ppcc.PatientCareCenter.Controllers.Admin;
    exports com.ppcc.PatientCareCenter.Controllers.Client;
    exports com.ppcc.PatientCareCenter.Controllers.LoginOrSignup;
    exports com.ppcc.PatientCareCenter.Controllers.Account;
    exports com.ppcc.PatientCareCenter.Model;
    exports com.ppcc.PatientCareCenter.Views;
    exports com.ppcc.PatientCareCenter.Views.Stages;
    exports com.ppcc.PatientCareCenter.Views.Components;
    exports com.ppcc.PatientCareCenter.Views.Panes;
    exports com.ppcc.PatientCareCenter.Database.User;
    exports com.ppcc.PatientCareCenter.Views.Components.PccTable;
    opens com.ppcc.PatientCareCenter.Views.Components.PccTable to javafx.fxml;
    exports com.ppcc.PatientCareCenter.Controllers.Admin.Patients;
    opens com.ppcc.PatientCareCenter.Controllers.Admin.Patients to javafx.fxml;
    exports com.ppcc.PatientCareCenter.Controllers.Admin.PharmacyStock;
    opens com.ppcc.PatientCareCenter.Controllers.Admin.PharmacyStock to javafx.fxml;
    exports com.ppcc.PatientCareCenter.Views.Components.DCConnection;
    opens com.ppcc.PatientCareCenter.Views.Components.DCConnection to javafx.fxml;
}

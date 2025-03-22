module com.ppcc.PatientCareCenter {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires com.dlsc.formsfx;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires net.sf.jasperreports.core;
    requires org.eclipse.jdt.core;
    requires org.jetbrains.annotations;
    requires javafx.swing;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.apache.pdfbox;
    requires com.formdev.flatlaf;

    opens com.pcc.PatientCareCenter to javafx.fxml;
    opens com.pcc.PatientCareCenter.Views to javafx.fxml;
    opens com.pcc.PatientCareCenter.Views.Panes to javafx.fxml;
    opens com.pcc.PatientCareCenter.Views.Stages to javafx.fxml;
    opens com.pcc.PatientCareCenter.Views.Components to javafx.fxml;
    opens com.pcc.PatientCareCenter.Controllers to javafx.fxml;
    opens com.pcc.PatientCareCenter.Controllers.LoginOrSignup to javafx.fxml;
    opens com.pcc.PatientCareCenter.Controllers.Client to javafx.fxml;
    opens com.pcc.PatientCareCenter.Controllers.Admin to javafx.fxml;
    opens com.pcc.PatientCareCenter.Database.User to javafx.base;
    opens com.pcc.PatientCareCenter.Database to javafx.base;
    exports com.pcc.PatientCareCenter;
    exports com.pcc.PatientCareCenter.Controllers.Admin;
    exports com.pcc.PatientCareCenter.Controllers.Client;
    exports com.pcc.PatientCareCenter.Controllers.LoginOrSignup;
    exports com.pcc.PatientCareCenter.Model;
    exports com.pcc.PatientCareCenter.Views;
    exports com.pcc.PatientCareCenter.Views.Stages;
    exports com.pcc.PatientCareCenter.Views.Components;
    exports com.pcc.PatientCareCenter.Views.Panes;
    exports com.pcc.PatientCareCenter.Database.User;
    exports com.pcc.PatientCareCenter.Views.Components.PccTable;
    exports com.pcc.PatientCareCenter.Database;
    opens com.pcc.PatientCareCenter.Views.Components.PccTable to javafx.fxml;
    exports com.pcc.PatientCareCenter.Controllers.Admin.Patients;
    opens com.pcc.PatientCareCenter.Controllers.Admin.Patients to javafx.fxml;
    exports com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock;
    opens com.pcc.PatientCareCenter.Controllers.Admin.PharmacyStock to javafx.fxml;
    exports com.pcc.PatientCareCenter.Views.Components.DCConnection;
    opens com.pcc.PatientCareCenter.Views.Components.DCConnection to javafx.fxml;
    exports com.pcc.PatientCareCenter.Views.Components.ReportPrinting;
    opens com.pcc.PatientCareCenter.Views.Components.ReportPrinting to javafx.fxml;
    exports com.pcc.PatientCareCenter.Controllers;
}
//java --module-path /Users/samadawalage/Downloads/javafx-sdk-24/lib --add-modules javafx.controls -jar PatientCareCenter.jar

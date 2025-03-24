package com.pcc.PatientCareCenter.Controllers.Admin;

import com.pcc.PatientCareCenter.Views.Components.DCConnection.DataComponentConnection;
import com.pcc.PatientCareCenter.Views.Components.DCConnection.StringTextAreaConnection;
import com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter.PccJRXmlLoader;
import com.pcc.PatientCareCenter.Views.GlobalsViews;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import net.sf.jasperreports.engine.JRException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LetterHeadController implements Initializable {
    public TextArea letter;
    public Button print;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        print.setOnAction(event -> {
            try {
                print();
            } catch (JRException | SQLException e) {
                GlobalsViews.showErrorAlert(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        });
    }
    public void print() throws JRException, SQLException {
        PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("Letter");
        jrXmlLoader.addParams(GlobalsViews.getLetterHead());
        jrXmlLoader.printWithDCConnection(
                Arrays.asList(
                        "letter"
                ),
                new DataComponentConnection[]{
                        new StringTextAreaConnection(letter)
                });
    }
}
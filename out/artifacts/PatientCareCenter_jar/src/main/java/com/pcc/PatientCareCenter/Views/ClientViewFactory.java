package com.pcc.PatientCareCenter.Views;

import com.pcc.PatientCareCenter.Views.Stages.StageViewFactory;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import static com.pcc.PatientCareCenter.Views.GlobalsViews.FXML_PATH;

public class ClientViewFactory extends StageViewFactory {

    public void showClientWindow() throws IOException {
        FXMLLoader fxmlFile = new FXMLLoader(getClass().getResource(FXML_PATH+"/Client/client.fxml"));
        createStage(fxmlFile.load());
    }

}

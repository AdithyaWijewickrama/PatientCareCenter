package com.ppcc.PatientCareCenter.Views;

import com.ppcc.PatientCareCenter.Views.Stages.StageViewFactory;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import static com.ppcc.PatientCareCenter.Views.GlobalsViews.FXML_PATH;

public class ClientViewFactory extends StageViewFactory {

    public void showClientWindow() throws IOException {
        FXMLLoader fxmlFile = new FXMLLoader(getClass().getResource(FXML_PATH+"/Client/client.fxml"));
        createStage(fxmlFile.load());
    }

}

package com.ppcc.PatientCareCenter.Views.Stages;

import com.ppcc.PatientCareCenter.Views.GlobalsViews;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StageViewFactory {
    protected StringProperty stageTitle;
    protected Image stageImage;

    protected synchronized Stage createStage(Parent pane){
        return GlobalsViews.createPrimaryStage(pane, stageTitle, stageImage);
    }
}

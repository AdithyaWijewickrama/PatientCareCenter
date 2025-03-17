package com.pcc.PatientCareCenter.Views.Components.PccTable;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;

public class ButtonElements {
    public static void bindIconFillProperty(Button button, String styleClass, FontAwesomeIconView icon) {
        button.setGraphic(icon);
        icon.fillProperty().bind(button.textFillProperty());
        button.getStyleClass().add(styleClass);
    }

}

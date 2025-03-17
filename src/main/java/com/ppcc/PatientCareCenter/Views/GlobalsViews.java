package com.ppcc.PatientCareCenter.Views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GlobalsViews {
    public static final int STAGE_WIDTH = 600;
    public static final int STAGE_HEIGHT = 400;
    public static final String APP_NAME = "Patient Care Center";
    public static final Color APP_COLOR = Color.NAVY;
    public static final Image APP_ICON = getImage(FontAwesomeIcon.USER_PLUS);
    public static final String FXML_PATH = "/com/ppcc/PatientCareCenter/Fxml";

    public static Stage createPrimaryStage(Parent pane, StringProperty title, Image icon) {
        Scene scene;
        scene = new Scene(pane);
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setTitle(title.getValue());
        title.addListener(observable -> {
            stage.setTitle(title.getValue());
        });
        stage.setScene(scene);
        return stage;
    }

    public static synchronized Parent loadFxml(String fxmlPath) {
        try {
            return new FXMLLoader(GlobalsViews.class.getResource(fxmlPath)).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Parent loadFxml(String fxmlPath, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GlobalsViews.class.getResource(fxmlPath));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getImage(FontAwesomeIcon icon) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(APP_COLOR);
        return iconView.snapshot(params, null);
    }

}

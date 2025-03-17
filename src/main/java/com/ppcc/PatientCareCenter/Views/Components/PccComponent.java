package com.ppcc.PatientCareCenter.Views.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PccComponent {
    public static final String ROOT_FXML_PATH = "/com/ppcc/PatientCareCenter/Fxml";
    protected String fxmlPath;
    protected FXMLLoader fxmlLoader;
    protected Node node;

    protected PccComponent(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    protected PccComponent(FXMLLoader loader) {
        this.fxmlLoader = loader;
    }

    protected PccComponent(Node node) {
        this.node = node;
    }

    public Node getComponent() {
        if (this.fxmlLoader == null) {
            this.fxmlLoader = new FXMLLoader(getClass().getResource(ROOT_FXML_PATH + fxmlPath));
        }
        try {
            if (this.node == null) {
                node = fxmlLoader.load();
            }
            return node;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOnWindow() {
        Scene scene = new Scene((Parent) getComponent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}

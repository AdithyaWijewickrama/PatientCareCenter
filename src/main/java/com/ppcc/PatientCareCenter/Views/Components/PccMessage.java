package com.ppcc.PatientCareCenter.Views.Components;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public  class PccMessage {
    public static void showMessage(Label label, String msg,MessageType messageType){
        if(label.isVisible())return;
        label.setText(msg);
        label.getStyleClass().clear();
        label.getStyleClass().add(messageType.messageClass);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setOnFinished(event -> {
            // After fading in, wait for 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                // Fade out the label
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), label);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(ev -> label.setVisible(false));
                fadeOut.play();
            });
            pause.play();
        });
        fadeIn.play();
        label.setVisible(true);
    }
}

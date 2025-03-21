package com.pcc.PatientCareCenter.Views.Components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldElements {
    TextField tf;
    Button btn;
    Label messageLabel;
    String message;

    public TextFieldElements(TextField tf, Button btn, Label messageLabel,String message){
        this.tf=tf;
        this.btn=btn;
        this.messageLabel=messageLabel;
        this.message=message;
    }

    public TextFieldElements setRequired(){
        setRequired(tf,btn,messageLabel,message);
        return this;
    }

    public TextFieldElements setAlphabetic(){
        setAlphabetic(tf,btn,messageLabel,message);
        return this;
    }

    public static void setRequired(TextField tf, Button btn, Label messageLabel, String message) {
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (tf.getText().isEmpty()) {
                event.consume();
                PccMessage.showMessage(messageLabel, message + " is required", MessageType.MESSAGE_TYPE_ERROR);
            }
        });
    }

    public static void setAlphabetic(TextField tf, Button btn, Label messageLabel, String message) {
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (!tf.getText().matches(".*[a-zA-Z].*")) {
                event.consume();
                PccMessage.showMessage(messageLabel, message + " must be alphabetic", MessageType.MESSAGE_TYPE_ERROR);
            }
        });
    }
}

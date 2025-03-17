package com.ppcc.PatientCareCenter.Views.Components;

public enum MessageType{
    MESSAGE_TYPE_ERROR("error-label"),
    MESSAGE_TYPE_INFO("info-label"),
    MESSAGE_TYPE_SUCCESS("success-label"),
    MESSAGE_TYPE_WARNING("warning-label");
    public final String messageClass;

    MessageType(String messageClass) {
        this.messageClass = messageClass;
    }
}

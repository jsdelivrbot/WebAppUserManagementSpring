package com.massimo.webapp.notifications;

public enum NotificationType {
    INFO("info"),
    DANGER("danger"),
    WARNING("warning"),
    SUCCESS("success");

    private final String status; // stores the data

    private NotificationType(String status) {  // <== private constuctor
        this.status = status;
    }

    public String getStatus() {  // <== allow access to the data
        return status;
    }
}


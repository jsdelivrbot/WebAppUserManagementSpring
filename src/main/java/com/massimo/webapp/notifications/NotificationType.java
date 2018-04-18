package com.massimo.webapp.notifications;

public enum NotificationType {
    INFO,
    ERROR,
    WARNING,
    SUCCESS;

    String notifcationType;

    public String getNotifcationType() {
        return notifcationType;
    }

    public void setNotifcationType(String notifcationType) {
        this.notifcationType = notifcationType;
    }
}

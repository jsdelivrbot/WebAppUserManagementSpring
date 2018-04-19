package com.massimo.webapp.notifications;

public enum NotificationType {
    info,
    danger,
    warning,
    success;

    String notifcationType;

    public String getNotifcationType() {
        return notifcationType;
    }

    public void setNotifcationType(String notifcationType) {
        this.notifcationType = notifcationType;
    }
}

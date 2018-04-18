package com.massimo.webapp.notifications;

public class Notifications {

    private String message;
    private String type = NotificationType.INFO.getNotifcationType();
    private int timer;

    public Notifications(String message, String type, int timer) {
        this.message = message;
        this.type = type;
        this.timer = timer;
    }

    public Notifications() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}

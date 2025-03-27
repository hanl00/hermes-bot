package com.hermesbot.database;

public class Reminder {
    private String userId;
    private long time;
    private String message;

    public Reminder(String userId, long time, String message) {
        this.userId = userId;
        this.time = time;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}

package com.example.smartfeeder;

public class ControlMotorRequest {
    private boolean force;
    private long timer;

    public ControlMotorRequest(boolean force, long timer) {
        this.force = force;
        this.timer = timer;
    }
}

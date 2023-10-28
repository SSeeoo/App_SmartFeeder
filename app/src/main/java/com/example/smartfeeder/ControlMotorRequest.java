package com.example.smartfeeder;

public class ControlMotorRequest {
    private final boolean force;
    private final long timer;

    public ControlMotorRequest(boolean force, long timer) {
        this.force = force;
        this.timer = timer;
    }
}

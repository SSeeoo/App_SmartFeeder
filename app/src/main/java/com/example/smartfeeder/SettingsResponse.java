package com.example.smartfeeder;

public class SettingsResponse {
    private int timeInterval;
    private String blockStartTime;
    private String blockEndTime;

    // 기본 생성자
    public SettingsResponse() {
    }

    // 모든 필드를 파라미터로 받는 생성자
    public SettingsResponse(int timeInterval, String blockStartTime, String blockEndTime) {
        this.timeInterval = timeInterval;
        this.blockStartTime = blockStartTime;
        this.blockEndTime = blockEndTime;
    }

    // getter 메서드
    public int getTimeInterval() {
        return timeInterval;
    }

    public String getBlockStartTime() {
        return blockStartTime;
    }

    public String getBlockEndTime() {
        return blockEndTime;
    }

    // setter 메서드
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setBlockStartTime(String blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    public void setBlockEndTime(String blockEndTime) {
        this.blockEndTime = blockEndTime;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "SettingsResponse{" +
                "timeInterval=" + timeInterval +
                ", blockStartTime='" + blockStartTime + '\'' +
                ", blockEndTime='" + blockEndTime + '\'' +
                '}';
    }
}

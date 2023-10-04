package com.example.smartfeeder;

public class FeedHistory {
    private String breed;
    private String time; // 또는 Date 타입을 사용할 수도 있습니다.

    public FeedHistory(String breed, String time) {
        this.breed = breed;
        this.time = time;
    }

    public String getBreed() {
        return breed;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Breed: " + breed + ", Time: " + time;
    }
}

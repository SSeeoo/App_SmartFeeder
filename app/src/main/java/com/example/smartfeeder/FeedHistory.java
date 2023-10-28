package com.example.smartfeeder;

public class FeedHistory {
    private String breed;
    private String time; // 또는 Date 타입을 사용할 수도 있습니다.
    private String feedAmount;

    public FeedHistory(String breed, String time, String feedAmount) {
        this.breed = breed;
        this.time = time;
        this.feedAmount = feedAmount;
    }

    public String getBreed() {
        return breed;
    }

    public String getTime() {
        return time;
    }

    public String getFeedAmount() {
        return feedAmount;
    }

    @Override
    public String toString() {
        return "Breed: " + breed + ", Time: " + time + ", Feed Amount: " + feedAmount;
    }
}

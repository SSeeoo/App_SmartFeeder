package com.example.smartfeeder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SmartFeederApi {

//    @POST("/dispense_feed")
//    Call<Void> dispenseFeed(long totalMillis);

    @POST("/feed")
    Call<Void> controlMotor(@Body ControlMotorRequest body);
}

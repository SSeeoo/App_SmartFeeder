package com.example.smartfeeder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("updateSettings")
    Call<Void> updateSettings(@Body UserSettings settings);
}

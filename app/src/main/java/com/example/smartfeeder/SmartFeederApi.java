package com.example.smartfeeder;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SmartFeederApi {
    @POST("/feed")  // 서버의 엔드포인트에 맞게 수정
    Call<Void> dispenseFeed(@Query("timer") int timer);  // 서버와 일치하는 매개변수를 사용
}

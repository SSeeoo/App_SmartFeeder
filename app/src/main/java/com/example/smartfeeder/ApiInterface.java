package com.example.smartfeeder;

import okhttp3.ResponseBody;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getFeedHistory")
    Call<List<FeedHistory>> getFeedHistory();

    @GET("getSettings")
    Call<SettingsResponse> getSettings();

    @FormUrlEncoded
    @POST("setSettings")
    Call<SettingsResponse> setSettings(
            @Field("timeInterval") int timeInterval,
            @Field("blockStartTime") String blockStartTime,
            @Field("blockEndTime") String blockEndTime
    );

    @POST("set_interval")
    Call<ResponseBody> setTimeInterval(@Body IntervalRequest intervalRequest);

    @POST("set_time_restriction")
    Call<ResponseBody> setTimeRestriction(@Body TimeRestrictionRequest timeRestrictionRequest);

    class IntervalRequest {
        public String user_id;
        public int interval_minutes;

        public IntervalRequest(String user_id, int interval_minutes) {
            this.user_id = user_id;
            this.interval_minutes = interval_minutes;
        }
    }

    class TimeRestrictionRequest {
        public String user_id;
        public String start_time;
        public String end_time;

        public TimeRestrictionRequest(String user_id, String start_time, String end_time) {
            this.user_id = user_id;
            this.start_time = start_time;
            this.end_time = end_time;
        }
    }
}

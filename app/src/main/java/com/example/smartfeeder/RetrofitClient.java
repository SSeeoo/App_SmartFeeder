package com.example.smartfeeder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://ec2-54-180-120-1.ap-northeast-2.compute.amazonaws.com:5000"; // ubuntu based aws ec2 server ip
    private static volatile Retrofit retrofit = null;


    private RetrofitClient() {
        // private constructor to prevent instantiation
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}

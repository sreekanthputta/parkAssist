package com.slabs.parkassist.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slabs.parkassist.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sreekanth Putta on 23-12-2017.
 */

public class RetrofitClient {

    private static String baseUrl = "http://"+ new Utils().url+"" +
            "/parkAssist/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
package com.slabs.parkassist.retrofit;

/**
 * Created by Sreekanth Putta on 23-12-2017.
 */

public class ApiUtils {
    public static RetrofitService getSOService() {
        return RetrofitClient.getClient().create(RetrofitService.class);
    }
}

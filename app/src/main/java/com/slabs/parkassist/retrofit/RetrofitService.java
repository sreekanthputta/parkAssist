package com.slabs.parkassist.retrofit;

import com.slabs.parkassist.models.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Sreekanth Putta on 23-12-2017.
 */

public interface RetrofitService {

    @GET("user.php?function=login")
    Call<Login> userLogin(@Query("userName") String userName, @Query("password") String password);

    @GET("user.php?function=register")
    Call<String> userRegistration(@Query("fullName") String fullName, @Query("userName") String userName, @Query("email") String email, @Query("password") String password, @Query("mobile") String mobile);

    @GET("answers?order=desc&sort=activity&site=stackoverflow")
    Call<String> getAnswers(@Query("tagged") String tags);
}
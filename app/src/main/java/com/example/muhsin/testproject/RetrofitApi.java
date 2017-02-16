package com.example.muhsin.testproject;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by MUHSIN on 2/14/2017.
 */

public interface RetrofitApi {
    @FormUrlEncoded
    @POST("/emc/log1.php")
    public void LoginUser(
            @Field("username") String username,
            @Field("password") String password,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/emc/ct.php")
    public void getCategory(
            @Field("idvalue") String idvalue,
            Callback<Response> callback);
}



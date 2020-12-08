package com.masterandroid.backgroundservice.retrofit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/Includes/InsertData.php")
    @FormUrlEncoded
    public Call<Response> insertUser(
            @Field("placeLongitude") String placeLongitude,
            @Field("placeLatitude") String placeLatitude,
            @Field("placeAddress") String placeAddress,
            @Field("city") String city);
}

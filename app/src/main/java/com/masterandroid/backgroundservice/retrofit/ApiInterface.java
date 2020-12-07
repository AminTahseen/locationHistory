package com.masterandroid.backgroundservice.retrofit;

import com.google.gson.JsonObject;
import com.masterandroid.backgroundservice.place;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("Api.php")
    Call<JsonObject> postLocation
            (
            @Query("apicall") String apicall,
            @Body RequestBody body
            );
}

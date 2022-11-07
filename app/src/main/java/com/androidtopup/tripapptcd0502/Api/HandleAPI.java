package com.androidtopup.tripapptcd0502.Api;

import com.androidtopup.tripapptcd0502.View.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HandleAPI {
//    @POST("english-levels")
//    Call<DetailList> createPost(@Body DetailList dataModal);

//    @Headers({
//            "Content-Type: application/x-www-form-urlencoded",
//            "Cache-Control: no-cache"
//    })
    @FormUrlEncoded
    @POST("COMP1424CW")
    Call<Data> createPost(@Field("jsonpayload") String jsonpayload);

}

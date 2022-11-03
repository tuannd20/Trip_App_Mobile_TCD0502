package com.androidtopup.tripapptcd0502.Api;

import com.androidtopup.tripapptcd0502.View.Data;
import com.androidtopup.tripapptcd0502.View.DetailList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HandleAPI {
//    @POST("english-levels")
//    Call<DetailList> createPost(@Body DetailList dataModal);

//    @Headers({"Content-Type: multipart/form-data","Content-Type: text/plain"})
//    @FormUrlEncoded
    @POST("COMP1424CW")
    Call<Data> createPost(@Body Data dataModal);

}

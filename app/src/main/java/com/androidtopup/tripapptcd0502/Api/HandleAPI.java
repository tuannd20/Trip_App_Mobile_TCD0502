package com.androidtopup.tripapptcd0502.Api;

import com.androidtopup.tripapptcd0502.View.Data;
import com.androidtopup.tripapptcd0502.View.DetailList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HandleAPI {
//    @POST("english-levels")
//    Call<DetailList> createPost(@Body DetailList dataModal);

    @POST("COMP1424CW")
    Call<Data> createPost(@Body Data dataModal);

}

package com.androidtopup.tripapptcd0502.Api;

import com.androidtopup.tripapptcd0502.View.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    ApiService apiService = new Retrofit.Builder()
                            .baseUrl("https://cwservice1786.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService.class);

    @POST("/sendPayLoad")
    Call<Data> sendJsonData(@Body Data data);
}

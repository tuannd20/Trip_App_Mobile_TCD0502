package com.androidtopup.tripapptcd0502.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Api.HandleAPI;
import com.androidtopup.tripapptcd0502.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadCloudFragment extends Fragment {

    View view;
    Button buttonUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);
        List<DetailList> detailList = new ArrayList<>();
        detailList.add(new DetailList("Mobile learning", "Test upload api"));
        detailList.add(new DetailList("Android learning", "Demo upload api"));
        buttonUpload = view.findViewById(R.id.buttonUpload);

//        Data payload = new Data("test1234", detailList);
//
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(payload);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData("test1234", detailList);
            }
        });
        return view;
    }

    private void postData(String userId, List<DetailList> detailList) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cw1-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HandleAPI retrofitAPI = retrofit.create(HandleAPI.class);
        Data jsonpayLoad = new Data(userId, detailList);

        Gson gson = new Gson();
        String jsonData = gson.toJson(jsonpayLoad);
        Log.i("String 02", jsonData);

        Call<Data> call = retrofitAPI.createPost(jsonData);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "Data added to API Successfully", Toast.LENGTH_SHORT).show();

                Data responseFromAPI = response.body();
                if (responseFromAPI != null) {
                    Log.i("data response", String.valueOf(response.body().getUserId()));
                    Log.i("data response", String.valueOf(response.body().getUploadResponseCode()));
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

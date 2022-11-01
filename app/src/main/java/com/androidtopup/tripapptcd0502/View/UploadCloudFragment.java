package com.androidtopup.tripapptcd0502.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Api.ApiService;
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
        buttonUpload = view.findViewById(R.id.buttonUpload);

        Data payload = new Data("test1234", detailList);

        Gson gson = new Gson();
        String jsonData = gson.toJson(payload);

        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(UploadCloudFragment.this.getContext());
        confirmAlert.setTitle("Delete");
        confirmAlert.setMessage(jsonData);
        confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        confirmAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        confirmAlert.create().show();

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData("Good123", "helolololololo");
            }
        });

        Log.i("data", jsonData);
        return view;
    }

    private void sendJsonData() {
        List<DetailList> detailList = new ArrayList<>();
        detailList.add(new DetailList("Mobile learning", "Test upload api"));

        Data payload = new Data("test1234", detailList);
        ApiService.apiService.sendJsonData(payload).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "Call Api well done", Toast.LENGTH_SHORT).show();

                Data result = response.body();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "Call Api Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postData(String level, String desc) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://platform-gw.dev.smartdev.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HandleAPI retrofitAPI = retrofit.create(HandleAPI.class);
        DetailList modal = new DetailList(level, desc);

        Call<DetailList> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<DetailList>() {
            @Override
            public void onResponse(Call<DetailList> call, Response<DetailList> response) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DetailList> call, Throwable t) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "Data added to API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

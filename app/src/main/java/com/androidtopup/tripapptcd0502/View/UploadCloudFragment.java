package com.androidtopup.tripapptcd0502.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Api.ApiService;
import com.androidtopup.tripapptcd0502.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadCloudFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);
        List<DetailList> detailList = new ArrayList<>();
        detailList.add(new DetailList("Mobile learning", "Test upload api"));

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
}

package com.androidtopup.tripapptcd0502.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Api.HandleAPI;
import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.textfield.TextInputLayout;
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
    Context context;
    ExpenseAppDataBaseHelper ExpenseDBUploadAPI;
    ArrayList<String> trip_name, trip_destination, trip_date;
    TextInputLayout userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);
        context = view.getContext();
        userId = view.findViewById(R.id.inputUserId);
//        final String strUserId = userId.getEditText().getText().toString().trim();

        trip_name = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();

        ExpenseDBUploadAPI = new ExpenseAppDataBaseHelper(context);
        handleStoreDataInArrays();

        List<DetailList> detailList = new ArrayList<>();
        List<DetailList.OtherDetails> otherDetails = new ArrayList<>();
        otherDetails.add(new DetailList.OtherDetails(trip_destination.toString(), trip_date.toString()));
        detailList.add(new DetailList(trip_name.toString(), otherDetails));
//        detailList.add(new DetailList("Android learning", "Demo upload api", "20/02/2001"));
        buttonUpload = view.findViewById(R.id.buttonUpload);

//        Data payload = new Data("test1234", detailList);
//
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(detailList);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final String strUserId = userId.getEditText().getText().toString().trim();
//                postData(strUserId, detailList);
                final String strUserId = userId.getEditText().getText().toString().trim();
                Gson gson = new Gson();
                String jsonData = gson.toJson(detailList);
                AlertDialog.Builder confirmAlert = new AlertDialog.Builder(UploadCloudFragment.this.requireContext());
                confirmAlert.setTitle("Upload Detail ");
                confirmAlert.setMessage("Are you want to Upload all trip data into cloud:" + "\nUserID: " + strUserId  +"\n All Trip: " + jsonData);
                confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String strUserId = userId.getEditText().getText().toString().trim();
                        postData(strUserId, detailList);
                    }
                });
                confirmAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                confirmAlert.create().show();
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
//                Toast.makeText(UploadCloudFragment.this.getContext(), "Data added to API Successfully", Toast.LENGTH_SHORT).show();

                Data responseFromAPI = response.body();
                if (responseFromAPI != null) {
                    Log.i("data response", String.valueOf(response.body().getUserId()));
                    Log.i("data response", String.valueOf(response.body().getUploadResponseCode()));

                    new AlertDialog.Builder(UploadCloudFragment.this.getContext()).setTitle("Success").setMessage(
                            "You Upload Successfully" + "\n uploadResponseCode: " + response.body().getUploadResponseCode()
                            + "\n userId: " + response.body().getUserId()
                            + "\n names: " + response.body().getNames()
                            + "\n number: " + response.body().getNumber()
                            + "\n message: " + response.body().getMessage()
                    ).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(UploadCloudFragment.this.getContext(), "API Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleStoreDataInArrays(){
        Cursor cursor = ExpenseDBUploadAPI.displayAllTripToUploadApi();
        if (cursor.getCount() == 0) {
            Toast.makeText(this.getContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                trip_name.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
            }
        }
    }
}

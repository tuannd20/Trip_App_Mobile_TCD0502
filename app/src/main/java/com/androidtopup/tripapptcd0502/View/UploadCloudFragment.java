package com.androidtopup.tripapptcd0502.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Api.HandleAPI;
import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.android.material.appbar.MaterialToolbar;
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

    MaterialToolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);
        toolbar = view.findViewById(R.id.topAppBar);
        toolbar.setTitle("Upload");
        context = view.getContext();
        userId = view.findViewById(R.id.inputUserId);

        trip_name = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();

        ExpenseDBUploadAPI = new ExpenseAppDataBaseHelper(context);
        handleStoreDataInArrays();

        List<DetailList> detailList = new ArrayList<>();
        List<DetailList.OtherDetails> otherDetails = new ArrayList<>();

        otherDetails.add(new DetailList.OtherDetails(trip_destination.toString(), trip_date.toString()));

        for(String nameTrip: trip_name) {
            detailList.add(new DetailList(nameTrip, otherDetails));
        }
        buttonUpload = view.findViewById(R.id.buttonUpload);

//        Data payload = new Data("test1234", detailList);
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(detailList);

        Gson gson = new Gson();
        String jsonData = gson.toJson(detailList);
        Log.i("Đaaddadada", jsonData);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strUserId = userId.getEditText().getText().toString().trim();

                if  (TextUtils.isEmpty(strUserId)){
                    userId.setError("UserId is empty");
                    showErrorInvalidDialog(view);
                    return;
                }

                Gson gson = new Gson();
                String jsonData = gson.toJson(detailList);

                AlertDialog.Builder builder = new AlertDialog.Builder(UploadCloudFragment.this.getContext(), R.style.AlertDialogTheme);
                view = LayoutInflater.from(UploadCloudFragment.this.getContext()).inflate(
                        R.layout.layout_confirm_dailog,
                        (ConstraintLayout)view.findViewById(R.id.layoutDialogContainer)
                );
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle)).setText("Payload details");
                ((TextView) view.findViewById(R.id.textMessage)).setText("\nUserID: " + strUserId  +"\n All Trip: " + jsonData);
                ((Button) view.findViewById(R.id.buttonYes)).setText("Upload");
                ((Button) view.findViewById(R.id.buttonNo)).setText("Cancel");
                ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_action_name);

                final AlertDialog alertDialog = builder.create();
                view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String strUserId = userId.getEditText().getText().toString().trim();
                        postTripData(strUserId, detailList);
                        alertDialog.dismiss();
                    }
                });

                view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                if (alertDialog.getWindow() != null){
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });
        return view;
    }

    private void postTripData(String userId, List<DetailList> detailList) {
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
                Data responseFromAPI = response.body();
                View viewUpload;
                if (responseFromAPI != null) {
                    Log.i("data response", String.valueOf(response.body().getUserId()));
                    Log.i("data response", String.valueOf(response.body().getUploadResponseCode()));

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UploadCloudFragment.this.getContext(), R.style.AlertDialogTheme);
                    view = LayoutInflater.from(UploadCloudFragment.this.getContext()).inflate(
                            R.layout.layout_success_dailog,
                            (ConstraintLayout)view.findViewById(R.id.layoutDialogContainer)
                    );
                    builder.setView(view);
                    ((TextView) view.findViewById(R.id.textTitle)).setText("Upload successfully");
                    ((TextView) view.findViewById(R.id.textMessage)).setText( "\n uploadResponseCode: " + response.body().getUploadResponseCode()
                            + "\n userId: " + response.body().getUserId()
                            + "\n names: " + response.body().getNames()
                            + "\n number: " + response.body().getNumber()
                            + "\n message: " + response.body().getMessage());
                    ((Button) view.findViewById(R.id.buttonSuccess)).setText(getResources().getString(R.string.okay));
                    ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.done);

                    final android.app.AlertDialog alertDialog = builder.create();

                    view.findViewById(R.id.buttonSuccess).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    if (alertDialog.getWindow() != null){
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialog.show();
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

    private void showErrorInvalidDialog(View view){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UploadCloudFragment.this.getContext(), R.style.AlertDialogTheme);
        view = LayoutInflater.from(UploadCloudFragment.this.getContext()).inflate(
                R.layout.layout_error_dailog,
                (ConstraintLayout)view.findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Invalid Data");
        ((TextView) view.findViewById(R.id.textMessage)).setText("User Id must not be empty");
        ((Button) view.findViewById(R.id.buttonAction)).setText("Close");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.error);

        final android.app.AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}

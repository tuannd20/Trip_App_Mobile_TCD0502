package com.androidtopup.tripapptcd0502.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.androidtopup.tripapptcd0502.Database.ExpenseAppDataBaseHelper;
import com.androidtopup.tripapptcd0502.R;
import com.google.gson.Gson;

public class UploadCloudFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);
        Data payload = new Data("SUCCESS", "wm123", 2, "Android Conference,Client Meeting", "successful upload – all done!");

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
}

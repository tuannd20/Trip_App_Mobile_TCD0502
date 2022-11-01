package com.androidtopup.tripapptcd0502.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidtopup.tripapptcd0502.R;

public class UploadCloudFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_cloud, container, false);


        return view;
    }

    private void postDataUsingVolley(String level, String desc) {
        String url = "https://platform-gw.dev.smartdev.dev/english-levels";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
    }
}

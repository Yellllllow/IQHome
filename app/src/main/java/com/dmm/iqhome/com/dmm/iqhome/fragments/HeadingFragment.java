package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dmm.iqhome.ISettingsHeader;
import com.dmm.iqhome.R;

/**
 * Created by waldekd on 2/1/16.
 */
public class HeadingFragment extends Fragment implements View.OnClickListener {
    private Button btnGlobalSettings;
    private Button btnDevices;
    private Button btnExit;
    private ISettingsHeader settingsHeader;

    public void setISettingHeader(ISettingsHeader sh) {
        settingsHeader = sh;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_headers, container, false);
        btnGlobalSettings = (Button) v.findViewById(R.id.btnGlobalSettings);
        btnDevices = (Button) v.findViewById(R.id.btnDevices);
        btnExit = (Button) v.findViewById(R.id.btnExit);


        btnGlobalSettings.setOnClickListener(this);
        btnDevices.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        return v;
    }


    public void onClick(View v) {
        settingsHeader.getClickedButtonFromHeader(v);
    }
}


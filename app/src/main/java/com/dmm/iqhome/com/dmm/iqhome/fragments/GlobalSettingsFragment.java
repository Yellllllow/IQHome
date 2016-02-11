package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmm.iqhome.R;

/**
 * Created by waldekd on 2/3/16.
 */
public class GlobalSettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.global_settings_fragment_layout, container, false);
        return v;
    }
}

package com.dmm.iqhome.com.dmm.iqhome.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmm.iqhome.R;

/**
 * Created by waldekd on 2/1/16.
 */
public class ContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_content, container, false);
    }
}

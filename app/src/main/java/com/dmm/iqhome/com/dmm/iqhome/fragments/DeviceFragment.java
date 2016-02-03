package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dmm.iqhome.Device;
import com.dmm.iqhome.R;
import com.dmm.iqhome.com.dmm.iqhome.adapters.DeviceSettingsAdapter;

import java.util.List;

/**
 * Created by waldekd on 2/2/16.
 */
public class DeviceFragment extends ListFragment {
    private ListView lvDeviceSettings;

    public static DeviceFragment newInstance(){
        DeviceFragment fr = new DeviceFragment();
        return fr;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_fragment_layout, container, false);

        List<Device> list = ((Settings)getActivity()).getDeviceList();

        final DeviceSettingsAdapter adapter = new DeviceSettingsAdapter(getActivity().getApplicationContext(), R.layout.device_settings_list_row, list);
        lvDeviceSettings = (ListView)v.findViewById(android.R.id.list);
        lvDeviceSettings.setAdapter(adapter);

        return v;
    }
}

package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dmm.iqhome.CommandManager;
import com.dmm.iqhome.Device;
import com.dmm.iqhome.ISettingsHeader;
import com.dmm.iqhome.R;

import java.util.List;

/**
 * Created by waldekd on 2/1/16.
 */
public class Settings extends FragmentActivity implements ISettingsHeader {
    private List<Device> deviceList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        Intent i = getIntent();
        if(i != null && i.hasExtra(CommandManager.DEVICE_LIST)){
            deviceList =  (List<Device>)i.getSerializableExtra(CommandManager.DEVICE_LIST);
        }


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.frHeaders) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HeadingFragment firstFragment = new HeadingFragment();
            firstFragment.setISettingHeader(this);
            DeviceFragment deviceFragmet = DeviceFragment.newInstance();


            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout

            getFragmentManager().beginTransaction().add(R.id.frHeaders, firstFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.frContent, deviceFragmet).commit();
        }
    }

    public List<Device> getDeviceList(){
        return deviceList;
    }

    @Override
    public void getClickedButtonFromHeader(View v) {
        if(v.getId() == R.id.btnExit){
            finish();
        }
    }
}

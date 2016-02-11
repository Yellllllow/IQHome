package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dmm.iqhome.CommandManager;
import com.dmm.iqhome.Device;
import com.dmm.iqhome.ISettingsHeader;
import com.dmm.iqhome.MainActivity;
import com.dmm.iqhome.R;

import java.util.List;

/**
 * Created by waldekd on 2/1/16.
 */
public class Settings extends FragmentActivity implements ISettingsHeader {
    private List<Device> deviceList = null;
    private HeadingFragment headingFragment = new HeadingFragment();
    private GlobalSettingsFragment globalSettingsFragment = new GlobalSettingsFragment();
    private DeviceFragment deviceFragment = new DeviceFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        Intent i = getIntent();
        if(i != null && i.hasExtra(CommandManager.DEVICE_LIST)){
            deviceList =  (List<Device>)i.getSerializableExtra(CommandManager.DEVICE_LIST);
        }

        if (findViewById(R.id.frHeaders) != null) {
            if (savedInstanceState != null) {
                return;
            }

            headingFragment.setISettingHeader(this);
            headingFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.frHeaders, headingFragment).commit();
        }

        changeContent(globalSettingsFragment);

    }


    public List<Device> getDeviceList(){
        return deviceList;
    }

    private void changeContent(Fragment newFragment){
        if(findViewById(R.id.frContent) != null){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frContent, newFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void getClickedButtonFromHeader(View v) {
        switch(v.getId()){
            case R.id.btnExit:
                finish();
                break;
            case R.id.btnGlobalSettings:
                changeContent(globalSettingsFragment);
                break;
            case R.id.btnDevices:
                changeContent(deviceFragment);
                break;
            default:
                break;
        }
    }
}

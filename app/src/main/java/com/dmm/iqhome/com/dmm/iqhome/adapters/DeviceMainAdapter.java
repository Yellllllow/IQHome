package com.dmm.iqhome.com.dmm.iqhome.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.dmm.iqhome.Device;
import com.dmm.iqhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waldekd on 2/7/16.
 */
public class DeviceMainAdapter extends DeviceSettingsAdapter {
    public DeviceMainAdapter(Context context, int resource, List<Device> objects) {
        super(context, resource, getAppripriateDevices(objects));
    }

    static public List<Device> getAppripriateDevices(List<Device> objects) {
        List<Device> ret = new ArrayList<>();
        for(Device d : objects){
            if(d.isActive()){
                ret.add(d);
            }
        }
        return ret;
    }


    private final int[] IMAGE_BUTTONS_TO_HIDE = {R.id.ibSpeakerEnable, R.id.ibRemoveEnable, R.id.ibSpeakerEnable};
    @Override
    protected void makeAdditionalChanges(View v) {
        TableLayout tl = (TableLayout)v.findViewById(R.id.tlSettingsTableLayout);
        tl.setVisibility(View.GONE);
        /*
        for(int i : IMAGE_BUTTONS_TO_HIDE){
            ImageButton imageButton = (ImageButton)v.findViewById(i);
            imageButton.setVisibility(View.GONE);
        }*/
    }
}

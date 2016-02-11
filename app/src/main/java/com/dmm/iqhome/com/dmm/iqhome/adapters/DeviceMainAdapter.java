package com.dmm.iqhome.com.dmm.iqhome.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

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


    private final int[] IMAGE_BUTTONS_TO_HIDE = {R.id.ibSpeaker, R.id.ibRemove, R.id.ibSpeaker};
    @Override
    protected void makeAdditionalChanges(View v) {
        for(int i : IMAGE_BUTTONS_TO_HIDE){
            ImageButton imageButton = (ImageButton)v.findViewById(R.id.ibSpeaker);
            imageButton.setVisibility(View.GONE);
        }
    }
}

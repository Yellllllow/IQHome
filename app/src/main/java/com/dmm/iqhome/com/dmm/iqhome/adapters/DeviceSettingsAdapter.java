package com.dmm.iqhome.com.dmm.iqhome.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmm.iqhome.Device;
import com.dmm.iqhome.R;

import java.util.List;

/**
 * Created by waldekd on 2/2/16.
 */
public class DeviceSettingsAdapter extends ArrayAdapter<Device> {
    private final Context context;
    private final List<Device> deviceList;
    private final int res;

    public DeviceSettingsAdapter(Context context, int resource, List<Device> objects) {
        super(context, resource, objects);
        this.context = context;
        this.deviceList = objects;
        this.res = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.device_settings_list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivImage);

        Device d = deviceList.get(position);
        textView.setText(d.Name);

        makeAdditionalChanges(rowView);

        if (d.isActive()) {
            imageView.setImageResource(R.mipmap.device_settings);
        } else {
            imageView.setImageResource(R.mipmap.device_settings_bw);
        }

        return rowView;
    }

    protected void makeAdditionalChanges(View v){
    }


}


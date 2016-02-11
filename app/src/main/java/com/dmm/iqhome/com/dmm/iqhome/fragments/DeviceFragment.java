package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dmm.iqhome.Device;
import com.dmm.iqhome.MainActivity;
import com.dmm.iqhome.R;
import com.dmm.iqhome.StatusUpdater;
import com.dmm.iqhome.com.dmm.iqhome.adapters.DeviceSettingsAdapter;
import com.dmm.iqhome.com.dmm.iqhome.interfaces.IReturnValueFromStatusUpdater;

import java.util.List;

/**
 * Created by waldekd on 2/2/16.
 */
public class DeviceFragment extends ListFragment implements IReturnValueFromStatusUpdater{
    private ListView lvDeviceSettings;
    private ProgressDialog progressDialog;

//
//    public static DeviceFragment newInstance(){
//        DeviceFragment fr = new DeviceFragment();
//        return fr;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_fragment_layout, container, false);

        List<Device> list = ((Settings)getActivity()).getDeviceList();

        final DeviceSettingsAdapter adapter = new DeviceSettingsAdapter(getActivity().getApplicationContext(), R.layout.device_settings_list_row, list);
        lvDeviceSettings = (ListView)v.findViewById(android.R.id.list);
        lvDeviceSettings.setAdapter(adapter);
        registerForContextMenu(lvDeviceSettings);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==android.R.id.list) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.devices_menu, menu);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Adapter adapter = lvDeviceSettings.getAdapter();
            Device item = (Device)adapter.getItem(info.position);

            menu.findItem(R.id.menuDevicesEnableDevice).setVisible(item.Active.equals("N"));
            menu.findItem(R.id.menuDevicesDisableDevice).setVisible(!item.Active.equals("N"));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        List<Device> list = ((Settings)getActivity()).getDeviceList();
        Device dev = list.get(info.position);
        boolean res = true;

        switch(item.getItemId()){
            case R.id.menuDevicesEnableDevice:
                if("N".equals(dev.Active)){
                    dev.Active = "Y";
                }else{
                    Log.d(MainActivity.TAG, "Trying to enable enabled device");
                    res = false;
                }
                break;
            case R.id.menuDevicesDisableDevice:
                if("Y".equals(dev.Active)){
                    dev.Active = "N";
                }else{
                    Log.d(MainActivity.TAG, "Trying to disable disabled device");
                    res = false;
                }
                break;
            case R.id.menuDeviceDeleteDevice:
                /* TODO */
                break;
            default:
                return super.onContextItemSelected(item);
        }
        if(res){
            progressDialog = ProgressDialog.show(getActivity(), "Updating devices...", "Please wait...");
            new StatusUpdater(getActivity(), DeviceFragment.this).execute(dev);
        }
        return true;
    }

        @Override
        public void GetValueReturnedByStatusUpdater(String s) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            String msg;
            switch (s) {
                case "OK":
                    msg = "Devices updated successfully";
                    break;
                case "NO_PARAMS":
                    msg = "No devices passed to updater";
                    break;
                case "FAIL":
                    msg = "Unable to update devices";
                    break;
                default:
                    msg = "Other problem";
                    break;
            }
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            ((DeviceSettingsAdapter)lvDeviceSettings.getAdapter()).notifyDataSetChanged();
        }


}

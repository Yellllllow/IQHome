package com.dmm.iqhome.com.dmm.iqhome.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dmm.iqhome.Device;
import com.dmm.iqhome.MainActivity;
import com.dmm.iqhome.R;
import com.dmm.iqhome.com.dmm.iqhome.adapters.DeviceMainAdapter;

import java.util.List;

/**
 * Created by waldekd on 2/7/16.
 */
public class DevicesMainFragment extends ListFragment {
    private ListView lvDevicesMain = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_devices_main, container, false);


        List<Device> list = ((MainActivity)getActivity()).getDeviceList();

        final DeviceMainAdapter adapter = new DeviceMainAdapter(getActivity().getApplicationContext(), R.layout.device_settings_list_row, list);
        lvDevicesMain = (ListView)v.findViewById(android.R.id.list);
        lvDevicesMain.setAdapter(adapter);

        registerForContextMenu(lvDevicesMain);

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        if (v.getId()==android.R.id.list) {
//            MenuInflater inflater = getActivity().getMenuInflater();
//            inflater.inflate(R.menu.devices_menu, menu);
//
//            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//            Adapter adapter = lvDeviceSettings.getAdapter();
//            Device item = (Device)adapter.getItem(info.position);
//
//            menu.findItem(R.id.menuDevicesEnableDevice).setVisible(item.Active.equals("N"));
//            menu.findItem(R.id.menuDevicesDisableDevice).setVisible(!item.Active.equals("N"));
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DeviceMainAdapter)lvDevicesMain.getAdapter()).notifyDataSetChanged();
    }


    @Override

    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch(item.getItemId()){
//            case R.id.menuDevicesEnableDevice:
//                return true;
//            case R.id.menuDevicesDisableDevice:
//                return true;
//            case R.id.menuDeviceDeleteDevice:
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
    }
}

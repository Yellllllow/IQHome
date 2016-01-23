package com.dmm.iqhome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waldekd on 1/23/16.
 */
public class Command {
    List<String> VoiceCommandsList = new ArrayList<>();
    List<Device> DevicesList = new ArrayList<>();

    public Command(List<String> voiceCommandsList, List<Device> devicesList) {
        VoiceCommandsList = voiceCommandsList;
        DevicesList = devicesList;
    }

    public void SetAllDevicesToState(String state){
        for(Device dev : DevicesList){
            dev.Value = state;
        }
    }
}

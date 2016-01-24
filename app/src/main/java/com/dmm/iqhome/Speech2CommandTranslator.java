package com.dmm.iqhome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waldekd on 1/23/16.
 */
public class Speech2CommandTranslator {
    private CommandManager commandManager;
    public Speech2CommandTranslator(CommandManager cm) {
        commandManager = cm;
    }

    public List<Device> GetDevicesForSpeech(String text){
        text = text.toLowerCase();
        List<Device> ret = new ArrayList<>();
        for(Command cmd : commandManager.EnableCommandList){
            if(cmd.VoiceCommandsList.contains(text)){
                cmd.SetAllDevicesToState("Y");
                ret.addAll(cmd.DevicesList);
            }
        }
        if(ret.isEmpty()) {
            for (Command cmd : commandManager.DisableCommandList) {
                if (cmd.VoiceCommandsList.contains(text)) {
                    cmd.SetAllDevicesToState("N");
                    ret.addAll(cmd.DevicesList);
                }
            }
        }
        return ret;
    }
}

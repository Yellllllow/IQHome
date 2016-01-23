package com.dmm.iqhome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by waldekd on 1/23/16.
 */
public class Speech2CommandTranslator {
    private List<Command> enableCommandList = new ArrayList<>();
    private List<Command> disableCommandList = new ArrayList<>();

    public Speech2CommandTranslator() {
        Device led1 = new Device("LED1");
        Device led2 = new Device("LED2");

        List<String> voiceCommandsEnable1 = Arrays.asList("włącz pierwszą", "włącz lewą", "włącz jeden", "włącz 1");
        List<String> voiceCommandsDisable1 = Arrays.asList("wyłącz pierwszą", "wyłącz lewą", "wyłącz jeden", "wyłącz 1");

        List<String> voiceCommandsEnable2 = Arrays.asList("włącz drugą", "włącz prawą", "włącz dwa", "włącz 2");
        List<String> voiceCommandsDisable2 = Arrays.asList("wyłącz drugą", "wyłącz prawą", "wyłącz drugą", "wyłącz 2");

        Command en1command = new Command(voiceCommandsEnable1, Arrays.asList(led1));
        Command dis1command = new Command(voiceCommandsDisable1, Arrays.asList(led1));
        Command en2command = new Command(voiceCommandsEnable2, Arrays.asList(led2));
        Command dis2command = new Command(voiceCommandsDisable2, Arrays.asList(led2));

        enableCommandList.add(en1command);
        enableCommandList.add(en2command);

        disableCommandList.add(dis1command);
        disableCommandList.add(dis2command);
    }

    public List<Device> GetDevicesForSpeech(String text){
        text = text.toLowerCase();
        for(Command cmd : enableCommandList){
            if(cmd.VoiceCommandsList.contains(text)){
                cmd.SetAllDevicesToState("Y");
                return cmd.DevicesList;
            }
        }

        for(Command cmd : disableCommandList){
            if(cmd.VoiceCommandsList.contains(text)){
                cmd.SetAllDevicesToState("N");
                return cmd.DevicesList;
            }
        }
        return null;
    }
}

package com.dmm.iqhome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by waldekd on 1/24/16.
 */
public class CommandManager {
    public List<Command> EnableCommandList = new ArrayList<>();
    public List<Command> DisableCommandList = new ArrayList<>();
    public List<Device> DeviceList = new ArrayList<>();


    public CommandManager() {
        //LED 1
        Device led1 = new Device("LED1");
        List<String> voiceCommandsEnable1 = Arrays.asList("włącz pierwszą", "włącz lewą", "włącz jeden", "włącz 1", "włącz pierwszy", "bądź pierwszy");
        List<String> voiceCommandsDisable1 = Arrays.asList("wyłącz pierwszą", "wyłącz lewą", "wyłącz jeden", "wyłącz 1", "wyłącz pierwszy", "bądź pierwszy");
        DeviceList.add(led1);

        //LED 2
        Device led2 = new Device("LED2");
        List<String> voiceCommandsEnable2 = Arrays.asList("włącz drugą", "włącz prawą", "włącz dwa", "włącz 2", "włącz drugi");
        List<String> voiceCommandsDisable2 = Arrays.asList("wyłącz drugą", "wyłącz prawą", "wyłącz drugą", "wyłącz 2", "wyłącz drugi");
        DeviceList.add(led2);


        Command en1command = new Command(voiceCommandsEnable1, Arrays.asList(led1));
        Command dis1command = new Command(voiceCommandsDisable1, Arrays.asList(led1));
        Command en2command = new Command(voiceCommandsEnable2, Arrays.asList(led2));
        Command dis2command = new Command(voiceCommandsDisable2, Arrays.asList(led2));

        EnableCommandList.add(en1command);
        EnableCommandList.add(en2command);

        DisableCommandList.add(dis1command);
        DisableCommandList.add(dis2command);
    }
}

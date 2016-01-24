package com.dmm.iqhome;

/**
 * Created by waldekd on 1/23/16.
 */
public class Device {
    public String Name;
    public String Value = "";

    public Device(String name, String value) {
        Name = name;
        Value = value;
    }

    public Device(String name) {

        Name = name;
    }


}

package com.dmm.iqhome;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waldekd on 1/23/16.
 */
public class Device implements Parcelable {
    public String Name;
    public String Value = "";
    public String Active = "";
    public String VoiceEnable = "";
    public String VoiceDisable = "";


    public Device(String name, String value, String active, String voiceEnable, String voiceDisable) {
        Name = name;
        Value = value;
        Active = active;
        VoiceEnable = voiceEnable;
        VoiceDisable = voiceDisable;
    }

    public Device(String name) {
        Name = name;

    }

    public boolean isEmpty(){
        return Name == null || Name.isEmpty() || Value == null || Value.isEmpty() || Active == null || Active.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Value);
        dest.writeString(this.Active);
        dest.writeString(this.VoiceEnable);
        dest.writeString(this.VoiceDisable);
    }

    protected Device(Parcel in) {
        this.Name = in.readString();
        this.Value = in.readString();
        this.Active = in.readString();
        this.VoiceEnable = in.readString();
        this.VoiceDisable = in.readString();
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public boolean isActive(){
        return "Y".equals(Active);
    }
}

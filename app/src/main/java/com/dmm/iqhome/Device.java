package com.dmm.iqhome;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by waldekd on 1/23/16.
 */
public class Device implements Parcelable {
    public String Name;
    public String Value = "";

    public Device(String name, String value) {
        Name = name;
        Value = value;
    }

    public Device(String name) {

        Name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Value);
    }

    protected Device(Parcel in) {
        this.Name = in.readString();
        this.Value = in.readString();
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}

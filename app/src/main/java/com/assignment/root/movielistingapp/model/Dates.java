package com.assignment.root.movielistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 19/7/17.
 */

public class Dates implements Parcelable {
    private String minimum;

    private String maximum;

    protected Dates(Parcel in) {
        minimum = in.readString();
        maximum = in.readString();
    }

    public static final Creator<Dates> CREATOR = new Creator<Dates>() {
        @Override
        public Dates createFromParcel(Parcel in) {
            return new Dates(in);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(minimum);
        parcel.writeString(maximum);
    }
}
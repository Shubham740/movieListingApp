package com.assignment.root.movielistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by user on 26/7/17.
 */

public class VideoKeyModel  implements Parcelable{
    private String id;
    private ArrayList<ResultsForVideo> results;

    protected VideoKeyModel(Parcel in) {
        id = in.readString();
        results = in.createTypedArrayList(ResultsForVideo.CREATOR);
    }

    public static final Creator<VideoKeyModel> CREATOR = new Creator<VideoKeyModel>() {
        @Override
        public VideoKeyModel createFromParcel(Parcel in) {
            return new VideoKeyModel(in);
        }

        @Override
        public VideoKeyModel[] newArray(int size) {
            return new VideoKeyModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ResultsForVideo> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultsForVideo> results) {
        this.results = results;
    }



    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeTypedList(results);
    }

    @Override
    public String toString() {
        return "VideoKeyModel{" +
                "id='" + id + '\'' +
                ", results=" + results +
                '}';
    }
}

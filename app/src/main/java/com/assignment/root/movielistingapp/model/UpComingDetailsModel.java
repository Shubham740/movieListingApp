package com.assignment.root.movielistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by root on 19/7/17.
 */

public class UpComingDetailsModel implements Parcelable {
    private ArrayList<Results> results;

    private Dates dates;

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public static Creator<UpComingDetailsModel> getCREATOR() {
        return CREATOR;
    }

    private String page;

    private String total_pages;

    private String total_results;


    protected UpComingDetailsModel(Parcel in) {
        results = in.createTypedArrayList(Results.CREATOR);
        dates = in.readParcelable(Dates.class.getClassLoader());
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    public static final Creator<UpComingDetailsModel> CREATOR = new Creator<UpComingDetailsModel>() {
        @Override
        public UpComingDetailsModel createFromParcel(Parcel in) {
            return new UpComingDetailsModel(in);
        }

        @Override
        public UpComingDetailsModel[] newArray(int size) {
            return new UpComingDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
        parcel.writeParcelable(dates, i);
        parcel.writeString(page);
        parcel.writeString(total_pages);
        parcel.writeString(total_results);
    }
}
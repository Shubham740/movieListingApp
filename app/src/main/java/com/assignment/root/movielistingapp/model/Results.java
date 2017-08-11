package com.assignment.root.movielistingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by root on 19/7/17.
 */

public class Results implements Parcelable {

    private String vote_average;

    private String backdrop_path;

    private String adult;
    private String overview;
    private String id;


    protected Results(Parcel in) {
        vote_average = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        overview = in.readString();
        id = in.readString();
        title = in.readString();
        original_language = in.readString();
        genre_ids = in.createStringArrayList();
        release_date = in.readString();
        original_title = in.readString();
        vote_count = in.readString();
        poster_path = in.readString();
        video = in.readString();
        popularity = in.readString();
        first_air_date = in.readString();
        name = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public ArrayList<String> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String title;


    private String original_language;

    private ArrayList<String> genre_ids;

    private String release_date;

    private String original_title;

    private String vote_count;

    private String poster_path;

    private String video;

    private String popularity;
    private String first_air_date;
    private String name;



    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Results)) {
            return false;
        }
        Results results= (Results) obj;
        return id.compareTo(results.id)==0;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote_average);
        dest.writeString(backdrop_path);
        dest.writeString(adult);
        dest.writeString(overview);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(original_language);
        dest.writeStringList(genre_ids);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(vote_count);
        dest.writeString(poster_path);
        dest.writeString(video);
        dest.writeString(popularity);
        dest.writeString(first_air_date);
        dest.writeString(name);
    }
}


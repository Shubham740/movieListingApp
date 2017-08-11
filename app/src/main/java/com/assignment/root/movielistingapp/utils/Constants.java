package com.assignment.root.movielistingapp.utils;

/**
 * Created by root on 19/7/17.
 */

public interface Constants {
    int UPCOMING_ACTION_ID = 1;
    String page = "&page=";
    String API_KEY = "?api_key=f0d6e4b96a02f84ffbd532763b0382e3&language=en-US";
    String URL_OF_MOVIE_LIST = "https://api.themoviedb.org/3/movie/upcoming" + API_KEY + page;
    String FILE_LOCATION = "data/data/DSD/pics";

    String FRAGMENT_TAG = "android:switcher:";
    String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    String VOTES = "Votes";
    String STORE_FILE_NAME = "STORE_FILE_NAME";
    int ACTION_ID_NOW_PLAYING_MOVIES = 5;
    int ACTION_ID_POPULAR_MOVIES = 6;
    int ACTION_ID_UPCOMING_MOVIES = 4;
    int ACTIOn_ID_POPULAR_TV_SHOWS = 7;
    String SAVE_LIST = "SAVE_LIST";
    int MOVIE_LIST_TOP_RATED = 3;
    int Action_ID_FOR_VIDEO = 2;
    String baseURl = "https://api.themoviedb.org/3/movie/";
    String VIDEOS = "/videos";
    String SEND_RESULT = "SEND_RESULT";
    String BaseUrlVidep = "https://api.themoviedb.org/3/tv/";
    String URL_OF_NOW_PLAYING_MOVIES = baseURl + "now_playing" + API_KEY + page;
    String URL_OF_POPULAR_MOVIES = baseURl + "popular" + API_KEY + page;
    String URL_OF_POPULAR_TV_SHOWS = BaseUrlVidep + "popular" + API_KEY + page;
    String URL_OF_TOP_RATED = baseURl + "top_rated" + API_KEY + page;

}

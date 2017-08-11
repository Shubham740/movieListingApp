package com.assignment.root.movielistingapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.assignment.root.movielistingapp.R;
import com.assignment.root.movielistingapp.model.Results;
import com.assignment.root.movielistingapp.model.ResultsForVideo;
import com.assignment.root.movielistingapp.model.VideoKeyModel;
import com.assignment.root.movielistingapp.utils.Constants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kelltontech.ui.IScreen;
import com.kelltontech.utils.ConnectivityUtils;
import com.kelltontech.utils.StringUtils;
import com.kelltontech.volley.ext.GsonObjectRequest;
import com.kelltontech.volley.ext.RequestManager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;


public class VideoPlayActivity extends YouTubeBaseActivity implements IScreen, YouTubePlayer.OnInitializedListener {
    private ArrayList<ResultsForVideo> mVideoAvailableList;
    private String mStringId;
    private YouTubePlayerView mYouTubePlayerView;
    private TextView mNameTextView;
    private Results mResults;
    private YouTubePlayer youTubePlayer;
    private TextView mOverViewTextView;
    private ScrollView scrollView;

    private TextView mReleasedDateTextView;
    private boolean isFullScreen;
    private TextView mTotalViewsTextViews;
    private YouTubePlayer mYouTubePlayer;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initLayout();
        if (savedInstanceState == null) {
            getIntentFromActivity();

            getData(Constants.Action_ID_FOR_VIDEO);
            setAllData();
            checkOrientation();
        }

    }

    private void checkOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollView.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void initLayout() {
        scrollView = (ScrollView) findViewById(R.id.scrollViewId);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        mNameTextView = (TextView) findViewById(R.id.textView_name_id);
        mOverViewTextView = (TextView) findViewById(R.id.textViewOverViewId);
        mReleasedDateTextView = (TextView) findViewById(R.id.releasedDateTextViewID);

        mTotalViewsTextViews = (TextView) findViewById(R.id.textViewTotalViewsId);
    }

    @Override
    public void updateUi(boolean status, int actionID, Object serviceResponse) {
        if (serviceResponse instanceof VideoKeyModel) {
            VideoKeyModel videoKeyModel = (VideoKeyModel) serviceResponse;
            mVideoAvailableList = videoKeyModel.getResults();
            mYouTubePlayerView.initialize(Constants.API_KEY, this);
        }
    }

    @Override
    public void onEvent(int eventId, Object eventData) {
        //not in use

    }

    /**
     * @param actionID this fucntion is used to hit the Api
     */
    @Override
    public void getData(int actionID) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            return;
        }
        if (actionID == Constants.Action_ID_FOR_VIDEO) {
            try {
                String totalUrl = Constants.baseURl + mStringId + Constants.VIDEOS + Constants.API_KEY;
                RequestManager.addRequest(new GsonObjectRequest<VideoKeyModel>(totalUrl, new HashMap<String, String>(), null, VideoKeyModel.class, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VideoPlayActivity.this, getResources().getString(R.string.ERROR_OCCUR), Toast.LENGTH_LONG);
                        //not in use
                    }
                }) {
                    @Override
                    protected void deliverResponse(VideoKeyModel response) {
                        updateUi(true, Constants.Action_ID_FOR_VIDEO, response);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    void getIntentFromActivity() {
        Intent intent = getIntent();
        if (intent != null) {
            mResults = getIntent().getParcelableExtra(Constants.SEND_RESULT);
            mStringId = mResults.getId().toString();
        }
    }

    /**
     * @param provider
     * @param youTubePlayer
     * @param wasRestored
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {

        this.youTubePlayer = youTubePlayer;
        YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.MINIMAL;
        youTubePlayer.setPlayerStyle(style);
        mYouTubePlayer = youTubePlayer;


        if (mVideoAvailableList.size() != 0) {


            if (mVideoAvailableList.size() != 0) {


                this.mYouTubePlayer.loadVideo(mVideoAvailableList.get(0).getKey());
                if (!wasRestored) {
                    this.mYouTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                            isFullScreen = b;
                            if (isFullScreen) {

                                scrollView.setVisibility(View.GONE);

                            } else {
                                youTubePlayer.setFullscreen(false);
                                setRequestedOrientation(
                                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                scrollView.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                }
            }
        } else
            Toast.makeText(VideoPlayActivity.this, getResources().getString(R.string.CANNOT_PLAY_VIDEO), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


    }


    @Override
    public void onBackPressed() {

        if (isFullScreen) {
            youTubePlayer.setFullscreen(false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }

    }

    private String getFormattedDate(String date) {
        String orignalDate = date;
        String splittedDate[] = orignalDate.split("-");
        int month = Integer.parseInt(splittedDate[1]);
        String yearString = splittedDate[0].substring(2);
        String monthString = (new DateFormatSymbols().getMonths()[month - 1]).substring(0, 3);
        String dateString;
        if (splittedDate[2].charAt(0) == '0') {
            dateString = String.valueOf(splittedDate[2].charAt(1));
        } else {
            dateString = splittedDate[2];
        }
        String finalDate = dateString + getDayNumberSuffix(Integer.parseInt(dateString)) +
                String.valueOf(" ") + monthString + String.valueOf(" ") + String.valueOf("'") + yearString + String.valueOf("'");
        return finalDate;
    }

    private String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollView.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollView.setVisibility(View.GONE); // or here
        }
    }

    private void setAllData() {
        if (!StringUtils.isNullOrEmpty(mResults.getTitle())) {
            mNameTextView.setText(mResults.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mReleasedDateTextView.setText(Html.fromHtml("<b>" + getResources().getString(R.string.RELEASED_DATE) + "</b>" + String.valueOf("  ") + getFormattedDate(mResults.getRelease_date()), Html.FROM_HTML_MODE_LEGACY));
            } else {
                mReleasedDateTextView.setText(Html.fromHtml("<b>" + getResources().getString(R.string.RELEASED_DATE) + "</b>" + String.valueOf("  ") + getFormattedDate(mResults.getRelease_date())));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mReleasedDateTextView.setText(Html.fromHtml("<b>" + getResources().getString(R.string.RELEASED_DATE) + "</b>" + String.valueOf("  ") + getFormattedDate(mResults.getFirst_air_date()), Html.FROM_HTML_MODE_LEGACY));
            } else {
                mReleasedDateTextView.setText(Html.fromHtml("<b>" + getResources().getString(R.string.RELEASED_DATE) + "</b>" + String.valueOf("  ") + getFormattedDate(mResults.getFirst_air_date())));
            }
            mNameTextView.setText(mResults.getName());
        }
        mOverViewTextView.setText(mResults.getOverview());


        mTotalViewsTextViews.setText(mResults.getVote_count() + String.valueOf(" ") + String.valueOf(getResources().getString(R.string.VIEWS)));
    }

}



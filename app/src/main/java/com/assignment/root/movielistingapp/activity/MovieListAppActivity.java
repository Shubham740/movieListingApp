package com.assignment.root.movielistingapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.assignment.root.movielistingapp.R;
import com.assignment.root.movielistingapp.adapter.ViewPagerAdapter;
import com.assignment.root.movielistingapp.fragment.LastViewedFragment;
import com.assignment.root.movielistingapp.fragment.MovieListingFragment;
import com.assignment.root.movielistingapp.fragment.MovieListingFragment.FragmentCommunicationListener;
import com.assignment.root.movielistingapp.fragment.MovieListingFragment.PageScrollListener;
import com.assignment.root.movielistingapp.model.Results;
import com.assignment.root.movielistingapp.model.UpComingDetailsModel;
import com.assignment.root.movielistingapp.utils.Constants;
import com.assignment.root.movielistingapp.utils.SaveDataThroughSharedPreferences;
import com.kelltontech.ui.IScreen;
import com.kelltontech.utils.ConnectivityUtils;
import com.kelltontech.volley.ext.GsonObjectRequest;
import com.kelltontech.volley.ext.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static com.assignment.root.movielistingapp.R.layout.activity_movie_listing_app;


public class MovieListAppActivity extends AppCompatActivity implements IScreen, PageScrollListener, FragmentCommunicationListener, LastViewedFragment.FragmentCommunicationListener, NavigationView.OnNavigationItemSelectedListener {

    private int mCheckActionId = Constants.UPCOMING_ACTION_ID;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LastViewedFragment mMovieLastViewedFragment;
    private int mPageCount = 1;
    private ViewPager mViewPager;
    private LinkedList<Results> mLastViewedLinkedList;
    private SaveDataThroughSharedPreferences mSaveDataThroughSharedPreferences;
    private DrawerLayout mDrawer;
    private ViewPagerAdapter mViewPagerAdapter;
    private ProgressBar mCircularProgressbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_movie_listing_app);
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCircularProgressbar = (ProgressBar) findViewById(R.id.ProgresscircularProgressbar);

        setSupportActionBar(this.mToolbar);
        this.mSaveDataThroughSharedPreferences = SaveDataThroughSharedPreferences.getNewInstance(this);
        this.mLastViewedLinkedList = this.mSaveDataThroughSharedPreferences.getSharedPReferenceLinkedList();
        initAndSetTabLayout();
        navigationViewSetUp();
        if (ConnectivityUtils.isNetworkEnabled(this)) {
            mCircularProgressbar.setVisibility(View.VISIBLE);
            getData(Constants.UPCOMING_ACTION_ID);
        } else {
            Toast.makeText(this, getResources().getString(R.string.CHECK_NETWROK_CONNECTION), Toast.LENGTH_LONG).show();
        }
    }

    private void initAndSetTabLayout() {
        String titleUpcoming = getResources().getString(R.string.tilteUpComing);
        String titleLastViewed = getResources().getString(R.string.lastViewed);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        MovieListingFragment movieListingFragment = new MovieListingFragment();
        mMovieLastViewedFragment = new LastViewedFragment();
        mViewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        mViewPagerAdapter.addFragments(movieListingFragment, titleUpcoming);
        mViewPagerAdapter.addFragments(this.mMovieLastViewedFragment, titleLastViewed);
        mViewPager.addOnPageChangeListener(mViewPagerAdapter);
        mViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(this.mViewPager);
        mLastViewedLinkedList = this.mSaveDataThroughSharedPreferences.getSharedPReferenceLinkedList();

    }

    public void updateUi(boolean status, int actionID, Object serviceResponse) {
        try {
            if (serviceResponse instanceof UpComingDetailsModel) {
                UpComingDetailsModel UpComingDetailsModel = (UpComingDetailsModel) serviceResponse;


                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_TAG + R.id.viewPager + ":" + mViewPager.getCurrentItem());
                if (fragmentByTag instanceof MovieListingFragment) {
                    ArrayList<Results> allData = UpComingDetailsModel.getResults();
                    ((MovieListingFragment) fragmentByTag).updatedResultDetails(allData);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEvent(int eventId, Object eventData) {
        // not in use
    }

    public void getData(int actionID) {
        switch (actionID) {
            case Constants.ACTION_ID_UPCOMING_MOVIES:
                mCircularProgressbar.setVisibility(View.VISIBLE);
                differentAPI(Constants.URL_OF_MOVIE_LIST, Constants.UPCOMING_ACTION_ID);
                break;
            case Constants.MOVIE_LIST_TOP_RATED:
                mCircularProgressbar.setVisibility(View.VISIBLE);


                differentAPI(Constants.URL_OF_TOP_RATED, Constants.MOVIE_LIST_TOP_RATED);
                break;

            case Constants.ACTION_ID_NOW_PLAYING_MOVIES:
                mCircularProgressbar.setVisibility(View.VISIBLE);

                differentAPI(Constants.URL_OF_NOW_PLAYING_MOVIES, Constants.ACTION_ID_NOW_PLAYING_MOVIES);
                break;
            case Constants.ACTION_ID_POPULAR_MOVIES:
                mCircularProgressbar.setVisibility(View.VISIBLE);

                differentAPI(Constants.URL_OF_POPULAR_MOVIES, Constants.ACTION_ID_POPULAR_MOVIES);
                break;
            case Constants.ACTIOn_ID_POPULAR_TV_SHOWS:
                mCircularProgressbar.setVisibility(View.VISIBLE);

                differentAPI(Constants.URL_OF_POPULAR_TV_SHOWS, Constants.ACTIOn_ID_POPULAR_TV_SHOWS);
                break;
            default: {

                differentAPI(Constants.URL_OF_MOVIE_LIST, Constants.UPCOMING_ACTION_ID);

                break;
            }

        }
    }

    /**
     * THis fuc
     * @param Url
     * @param actionID
     */
    public void differentAPI(String Url, int actionID) {
        final int diffrentActionId = actionID;
        RequestManager.addRequest(new GsonObjectRequest<UpComingDetailsModel>(Url + String.valueOf(this.mPageCount), new HashMap(), null, UpComingDetailsModel.class, new RequestManagerErrorListener()) {
            protected void deliverResponse(UpComingDetailsModel response) {
                MovieListAppActivity.this.updateUi(true, diffrentActionId, response);
                mCircularProgressbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * on scroll this functon get called  and page count will increase and than again one by one different api get hit and shown in the recycler view
     *
     * @param pageCount
     */
    public void onScroll(int pageCount) {
        this.mPageCount = pageCount;

        switch (this.mCheckActionId) {

            case Constants.UPCOMING_ACTION_ID:
                getData(Constants.UPCOMING_ACTION_ID);


                break;
            case Constants.MOVIE_LIST_TOP_RATED:
                getData(Constants.MOVIE_LIST_TOP_RATED);
                break;
            case Constants.ACTION_ID_NOW_PLAYING_MOVIES:
                getData(Constants.ACTION_ID_NOW_PLAYING_MOVIES);

                break;
            case Constants.ACTION_ID_UPCOMING_MOVIES:
                getData(Constants.ACTION_ID_UPCOMING_MOVIES);

                break;
            case Constants.ACTION_ID_POPULAR_MOVIES:
                getData(Constants.ACTION_ID_POPULAR_MOVIES);
                break;
            case Constants.ACTIOn_ID_POPULAR_TV_SHOWS:
                getData(Constants.ACTIOn_ID_POPULAR_TV_SHOWS);

                break;
            default: {

                getData(Constants.UPCOMING_ACTION_ID);
                break;
            }
        }


    }

    /**
     * @param results
     */
    public void onFragmentCommunicate(final Results results) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                if (mLastViewedLinkedList.size() == 20) {
                    if (mLastViewedLinkedList.contains(results)) {
                        removeAndAddElementInLinkedList(results);
                    } else {
                        mLastViewedLinkedList.removeLast();
                        mLastViewedLinkedList.addFirst(results);
                    }
                } else if (mLastViewedLinkedList.contains(results)) {
                    removeAndAddElementInLinkedList(results);
                } else {
                    mLastViewedLinkedList.addFirst(results);
                }
                mSaveDataThroughSharedPreferences.savedSharedPreferences(MovieListAppActivity.this.mLastViewedLinkedList);
                return null;
            }

            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                MovieListAppActivity.this.mMovieLastViewedFragment.setLastViewedList(MovieListAppActivity.this.mLastViewedLinkedList);
                Intent intent = new Intent(MovieListAppActivity.this, VideoPlayActivity.class);
                intent.putExtra(Constants.SEND_RESULT, results);
                MovieListAppActivity.this.startActivity(intent);
            }
        }.execute();
    }


    public void onBackPressed() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        mSaveDataThroughSharedPreferences.savedSharedPreferences(this.mLastViewedLinkedList);
        super.onBackPressed();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mSaveDataThroughSharedPreferences.savedSharedPreferences(mLastViewedLinkedList);
            super.onBackPressed();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**
     * respective api get git when on navigation view an item is clicked
     *
     * @param item
     * @return
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.top_rated_movies: {
                mCheckActionId = Constants.MOVIE_LIST_TOP_RATED;
                mViewPager.setCurrentItem(0);
                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.TOP_RATED));
                getData(Constants.MOVIE_LIST_TOP_RATED);
                break;
            }
            case R.id.upcoming_movies: {
                mCheckActionId = Constants.ACTION_ID_UPCOMING_MOVIES;
                mViewPager.setCurrentItem(0);

                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.UPCOMING));
                getData(Constants.ACTION_ID_UPCOMING_MOVIES);
                break;
            }
            case R.id.Now_playing_movies: {
                mCheckActionId = Constants.ACTION_ID_NOW_PLAYING_MOVIES;
                mViewPager.setCurrentItem(0);
                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.NOW_PLAYING));
                getData(Constants.ACTION_ID_NOW_PLAYING_MOVIES);
                break;
            }
            case R.id.polpuar_movies: {
                mCheckActionId = Constants.ACTIOn_ID_POPULAR_TV_SHOWS;
                mViewPager.setCurrentItem(0);
                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.POPULAR_MOVIES));
                getData(Constants.ACTION_ID_POPULAR_MOVIES);
                break;
            }
            case R.id.Popular_tv_shows: {
                mCheckActionId = Constants.ACTIOn_ID_POPULAR_TV_SHOWS;
                mViewPager.setCurrentItem(0);
                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.POPULAR_TV_SHOWS));
                getData(Constants.ACTIOn_ID_POPULAR_TV_SHOWS);
                break;
            }
            case R.id.aboutAppId: {
                setAlertDialog();
                break;
            }
            default: {
                mCheckActionId = Constants.UPCOMING_ACTION_ID;
                mViewPager.setCurrentItem(0);

                setFragment();
                mViewPagerAdapter.setTitle(getResources().getString(R.string.UPCOMING));
                getData(Constants.ACTION_ID_UPCOMING_MOVIES);
                break;
            }

        }

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);

        return true;
    }

    private void setAlertDialog() {
        final Dialog dialog = new Dialog(MovieListAppActivity.this);
        dialog.setTitle(getResources().getString(R.string.Title));
        dialog.setContentView(R.layout.layout_about_app);
        TextView appNameTextView = (TextView) dialog.findViewById(R.id.aboutAppTitleTextViewId);
        TextView versionNameTextView = (TextView) dialog.findViewById(R.id.aboutAppVersionTextViewId);
        appNameTextView.setText(getResources().getString(R.string.app_name));
        versionNameTextView.setText(getVersion());
        Button button = (Button) dialog.findViewById(R.id.aboutAppOkButtonTextViewId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void navigationViewSetUp() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, this.mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.mDrawerLayout.addDrawerListener(toggle);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.upcoming_movies);
        navigationView.setItemIconTintList(null);

    }

    /**
     * @return version of the app
     */
    private String getVersion()

    {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void setFragment() {
        Fragment page = getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_TAG + R.id.viewPager + ":" + mViewPager.getCurrentItem());
        if (page instanceof MovieListingFragment) {
            ((MovieListingFragment) page).clearListOfFragment();
            this.mPageCount = 1;
        }
    }

    public void removeAndAddElementInLinkedList(Results results) {
        this.mLastViewedLinkedList.remove(results);
        this.mLastViewedLinkedList.addFirst(results);
    }

    private class RequestManagerErrorListener implements com.android.volley.Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(MovieListAppActivity.this, getResources().getString(R.string.ERROR_OCCUR), Toast.LENGTH_LONG).show();

        }
    }

}











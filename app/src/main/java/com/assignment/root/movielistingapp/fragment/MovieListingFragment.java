package com.assignment.root.movielistingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.assignment.root.movielistingapp.R;
import com.assignment.root.movielistingapp.adapter.RecyclerViewAdapterForFragments;
import com.assignment.root.movielistingapp.model.Results;

import java.util.ArrayList;
import java.util.List;

//import static com.assignment.root.movielistingapp.R.id.circularProgressbar;
//import static com.assignment.root.movielistingapp.R.id.linearLayoutId;


public class MovieListingFragment extends Fragment implements RecyclerViewAdapterForFragments.ClickedItemListener{
    private RecyclerView mRecyclerViewOfMovieListing;
    private LinearLayoutManager mLayoutManager;
    private int mCount = 1;
    private View mView;
    private List<Results> mResultsArrayList;
    private FragmentCommunicationListener mFragmentCommunicationListener;
    private RecyclerViewAdapterForFragments recyclerViewAdapterForFragments;
    private SwipeRefreshLayout swipeLayout;
    private ProgressBar mCircularProgressbar;
    private LinearLayout mlinearLAyout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setRecyclerView() {
        recyclerViewAdapterForFragments = new RecyclerViewAdapterForFragments(mResultsArrayList, getActivity(), MovieListingFragment.this, true);
        mRecyclerViewOfMovieListing.setAdapter(recyclerViewAdapterForFragments);

        mRecyclerViewOfMovieListing.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /*    * This method is the method scroll listener interface which gives you the requested page request
                             * argument : pageCount
                             * return type : void
                           * * argument type :int     * */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {



                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        PageScrollListener pageScrollListenr = (PageScrollListener) getActivity();
                        pageScrollListenr.onScroll(++mCount);
                        mCircularProgressbar.setVisibility(View.VISIBLE);

                    }
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate
                (R.layout.fragment_movie_listing, container, false);

        initLayout();
        return mView;
    }

    @Override
    public void onClickedItem(Results results) {
        mFragmentCommunicationListener.onFragmentCommunicate(results);

    }


    /*
    *This method is used for update the recyclerView on back to back api hit
    * argument resultses
    * reture type: Void
    * argument type :int
     */
    public void updatedResultDetails(List<Results> resultses)

    {
        //this.mResultsArrayList = resultses;
        //setRecyclerView();

        mResultsArrayList.addAll(resultses);
     //

        recyclerViewAdapterForFragments.setmResultsDetailsArrayList(mResultsArrayList);
        mCircularProgressbar.setVisibility(View.GONE);
        recyclerViewAdapterForFragments.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentCommunicationListener = (FragmentCommunicationListener) context;
    }

    public void initLayout() {
        mRecyclerViewOfMovieListing = (RecyclerView) mView.findViewById(R.id.fragmentMovieListingRecyclerViewId);
        mCircularProgressbar= (ProgressBar) mView.findViewById(R.id.mCircularProgressbar);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewOfMovieListing.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), mLayoutManager.VERTICAL);
        mRecyclerViewOfMovieListing.addItemDecoration(itemDecoration);
        mResultsArrayList = new ArrayList<>();
        setRecyclerView();
    }

    public void clearListOfFragment() {
        mCount = 1;
        this.mResultsArrayList.clear();
        recyclerViewAdapterForFragments.notifyDataSetChanged();
    }





    public interface PageScrollListener {
        void onScroll(int pageCount);

    }

    /*
    * this interface is used to communicate the fragment  to add and remove the list
    *
     */
    public interface FragmentCommunicationListener {
        void onFragmentCommunicate(Results results);
    }




}

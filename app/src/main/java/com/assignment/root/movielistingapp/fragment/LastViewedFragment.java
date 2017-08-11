package com.assignment.root.movielistingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.root.movielistingapp.R;
import com.assignment.root.movielistingapp.adapter.RecyclerViewAdapterForFragments;
import com.assignment.root.movielistingapp.model.Results;
import com.assignment.root.movielistingapp.utils.SaveDataThroughSharedPreferences;

import java.util.LinkedList;

public class LastViewedFragment extends Fragment implements RecyclerViewAdapterForFragments.ClickedItemListener {
    private View view;

    public SaveDataThroughSharedPreferences mSaveDataThroughSharedPreferences;
    private LinkedList<Results> mLastViewedLinkedList=new LinkedList<>();
    private MovieListingFragment.FragmentCommunicationListener fragmentCommunicationListener;
    private RecyclerViewAdapterForFragments mRecyclerViewAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaveDataThroughSharedPreferences = SaveDataThroughSharedPreferences.getNewInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        initAndSetRecyclerView();
        return view;


    }

    private void initAndSetRecyclerView() {
        RecyclerView mRecyclerViewOfLastViewed = (RecyclerView) view.findViewById(R.id.fragmentLastViewedRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewOfLastViewed.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerViewOfLastViewed.addItemDecoration(itemDecoration);
     mRecyclerViewAdapter = new RecyclerViewAdapterForFragments(mLastViewedLinkedList, getActivity(), LastViewedFragment.this, false);

        mRecyclerViewOfLastViewed.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerViewOfLastViewed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
    }

    @Override
    public void onClickedItem(Results results) {
        fragmentCommunicationListener.onFragmentCommunicate(results);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicationListener = (MovieListingFragment.FragmentCommunicationListener) context;

    }

    public void setLastViewedList(LinkedList<Results> linkedList) {
        mLastViewedLinkedList.clear();
        mLastViewedLinkedList.addAll( SaveDataThroughSharedPreferences.getNewInstance(getActivity()).getSharedPReferenceLinkedList());
        mRecyclerViewAdapter.notifyDataSetChanged();
    }


    public interface FragmentCommunicationListener {

        void onFragmentCommunicate(Results results);
    }


}


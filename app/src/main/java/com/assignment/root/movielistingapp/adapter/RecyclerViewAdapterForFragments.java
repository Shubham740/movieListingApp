package com.assignment.root.movielistingapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.root.movielistingapp.R;
import com.assignment.root.movielistingapp.model.Results;
import com.assignment.root.movielistingapp.utils.Constants;
import com.kelltontech.utils.StringUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapterForFragments extends RecyclerView.Adapter<RecyclerViewAdapterForFragments.CustomViewHolder> {
    private ClickedItemListener mClickedItemListener;
    private Context mContext;
    private List<Results> mResultsDetailsArrayList;


    public RecyclerViewAdapterForFragments(List<Results> mResultsDetailsArrayList, Context mContext, Fragment mCurrentragment, boolean isClickedAvailable) {
        this.mResultsDetailsArrayList = mResultsDetailsArrayList;
        this.mClickedItemListener = (ClickedItemListener) mCurrentragment;
        this.mContext = mContext;


    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPoster;
        TextView textViewDescription;
        TextView textViewMovieName;
        TextView textViewReleasedDate;
        TextView textViewVotes;
        ViewGroup viewGroupListItem;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textViewMovieName = (TextView) itemView.findViewById(R.id.textViewMovieName);
            this.textViewDescription = (TextView) itemView.findViewById(R.id.textViewMovieDescription);
            this.textViewVotes = (TextView) itemView.findViewById(R.id.textViewMovieNumberVotesId);
            this.textViewReleasedDate = (TextView) itemView.findViewById(R.id.textViewMovieReleaseDate);
            this.imageViewPoster = (ImageView) itemView.findViewById(R.id.imageViewMoviePicture);
            this.viewGroupListItem = (ViewGroup) itemView.findViewById(R.id.list_itemId);
        }
    }

    public void setmResultsDetailsArrayList(List<Results> mResultsDetailsArrayList) {
        this.mResultsDetailsArrayList = mResultsDetailsArrayList;
    }

    public interface ClickedItemListener {
        void onClickedItem(Results results);
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_row_for_listing_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        String title;
        String releaseDate;

        Results results = this.mResultsDetailsArrayList.get(holder.getAdapterPosition());
        if (!StringUtils.isNullOrEmpty(results.getTitle())) {
            title = results.getTitle();
            releaseDate = results.getRelease_date();
        } else {
            title = results.getName();
            releaseDate = results.getFirst_air_date();
        }

        holder.textViewMovieName.setText(title);
        holder.textViewReleasedDate.setText(releaseDate);
        holder.textViewDescription.setText((results.getOverview()));

        holder.textViewVotes.setText(String.valueOf((results.getVote_count()) + String.valueOf(" ") + String.valueOf(Constants.VOTES)));
        Picasso.with(this.mContext)
                .load(Constants.IMAGE_BASE_URL + String.valueOf(results.getPoster_path()))
                .networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imageViewPoster);

        holder.viewGroupListItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RecyclerViewAdapterForFragments.this.mClickedItemListener.onClickedItem((Results) RecyclerViewAdapterForFragments.this.mResultsDetailsArrayList.get(position));


            }

        });
    }

    public int getItemCount() {
        return this.mResultsDetailsArrayList != null ? this.mResultsDetailsArrayList.size() : 0;
    }

}

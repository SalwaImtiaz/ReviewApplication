package com.intellisense.review.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.intellisense.review.R;
import com.intellisense.review.db_classes.Review;

import java.util.List;

/**
 * Created by user on 12/1/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;

    private List<Review> reviewsList;

    private final ListItemClickListener mClickListener;

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }

    public ReviewAdapter(List<Review> reviewsList, Context context, ListItemClickListener clickListener)
    {
        this.reviewsList = reviewsList;
        mContext = context;
        mClickListener = clickListener;
    }

    public void setReviewsList(List<Review> reviewsList)
    {
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.review_item;
        boolean shouldAttachToParentImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);

        return reviewViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review currentReview = reviewsList.get(position);
        holder.customerNameTextView.setText(currentReview.getCustomer_name());
        holder.reviewDetailTextView.setText(currentReview.getSuggestion());
        holder.ratingBar.setRating(currentReview.getRating());
    }



    @Override
    public int getItemCount() {

        return reviewsList.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView customerNameTextView;
        TextView reviewDetailTextView;
        RatingBar ratingBar;

        public ReviewViewHolder(View itemView)
        {
            super(itemView);

            customerNameTextView = (TextView)itemView.findViewById(R.id.review_customer_name);
            reviewDetailTextView = (TextView)itemView.findViewById(R.id.review_detail);
            ratingBar = (RatingBar)itemView.findViewById(R.id.review_rating_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onListItemClick(clickedPosition);
        }

    }
}

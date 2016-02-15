package com.anuj.twitter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuj.twitter.R;
import com.anuj.twitter.models.Timeline;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anujacharya on 2/15/16.
 */
public class TwitterTimelineAdapter extends RecyclerView.Adapter<TwitterTimelineAdapter.ViewHolder> {

    List<Timeline> timelineList;
    private Context context;


    public TwitterTimelineAdapter(List<Timeline> timelineList) {
        this.timelineList = timelineList;
    }

    @Override
    public TwitterTimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        TwitterTimelineAdapter.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v1 = inflater.inflate(R.layout.layout_timeline, parent, false);
        viewHolder = new ViewHolder(v1);

        viewHolder.tweetTxt.setText("ANUJ");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Timeline contact = timelineList.get(position);

        TextView tweetTxt = holder.tweetTxt;


    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    /*
        SET UP THE STATIC CLASS AND ON CLICK IN THE ADAPTER
     */
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.usrImg)
        public ImageView userTweetImg;
        @Bind(R.id.tweetTextView)
        public TextView tweetTxt;

        public ViewHolder(final View itemView) {
            super(itemView);

            // bind the item view
            ButterKnife.bind(this, itemView);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}

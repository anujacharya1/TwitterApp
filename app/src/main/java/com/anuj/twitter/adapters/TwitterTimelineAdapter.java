package com.anuj.twitter.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuj.twitter.R;
import com.anuj.twitter.models.Timeline;
import com.anuj.twitter.models.User;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anujacharya on 2/15/16.
 */
public class TwitterTimelineAdapter extends RecyclerView.Adapter<TwitterTimelineAdapter.ViewHolder> {

    List<Timeline> timelineList;
    private Context context;

    static final String TWITTER_TIME_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy";

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

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Timeline timeline = timelineList.get(position);
        User user = timeline.getUser();

        holder.tweetTxt.setText(timeline.getText());
        holder.username.setText(user.getName());

        Date createdAt = getTwitterDate(timeline.getCreatedAt());
        //date
        long now = System.currentTimeMillis();


        Calendar c = Calendar.getInstance();
        c.setTime(createdAt);
        long time = c.getTimeInMillis();


        CharSequence timeFormated =  DateUtils.getRelativeTimeSpanString(
                time, now,
                DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_NO_NOON);
//
//        Drawable dr = getContext().getResources().getDrawable(R.drawable.clock);
//        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//        Drawable d = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

//        holder.date.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
        holder.date.setText(timeFormated.toString());
        holder.date.setTextSize(10.0f);


        Glide.with(context)
                .load(user.getProfileImg())
                .override(50, 50)
                .into(holder.userTweetImg)
                ;

    }

    public static Date getTwitterDate(String date) {
//Thu Dec 23 18:26:07 +0000 2010
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER_TIME_FORMAT);
        sf.setLenient(true);
        Date twitterDate = null;
        try {
            twitterDate = sf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twitterDate;
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
        @Bind(R.id.tvTweet)
        public TextView tweetTxt;
        @Bind(R.id.tvDate)
        TextView date;
        @Bind(R.id.tvUsername)
        TextView username;

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

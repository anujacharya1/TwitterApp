package com.anuj.twitter.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuj.twitter.R;
import com.anuj.twitter.models.Timeline;
import com.anuj.twitter.models.User;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anujacharya on 2/20/16.
 */
public class TweetDetailActivity extends AppCompatActivity {

    ImageView ivDetailProfilePic;
    TextView tvDetailUsername;
    TextView tvDeatilTweet;
    TextView tvDetailScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Timeline timeline = Parcels.unwrap(getIntent().getParcelableExtra("timeline"));
        setContentView(R.layout.activity_tweet_detail);

        Toolbar twitterToolBar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(twitterToolBar);
        twitterToolBar.setBackgroundColor(getResources().getColor(R.color.blue));
        twitterToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        twitterToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivDetailProfilePic = (ImageView) findViewById(R.id.ivDetailProfilePic);
        tvDetailUsername =(TextView) findViewById(R.id.tvDetailUsername);
        tvDeatilTweet = (TextView) findViewById(R.id.tvDeatilTweet);
        tvDetailScreenName = (TextView) findViewById(R.id.tvDetailScreenName);

        User user = timeline.getUser();

        //tweet
        tvDeatilTweet.setText(timeline.getText());

        //username
        tvDetailUsername.setText(user.getName());
        tvDetailUsername.setText(Html.fromHtml("<b><font size='1' color='#000000'>"
                + user.getName() + "</font></b>"));
        tvDetailScreenName.setText(Html.fromHtml("<font size='0.2' color='#9E9E9E'>" +
                "@" + user.getScreenName() + "</font>"));

        //profile image
        Glide.with(this)
                .load(user.getProfileImg())
                .into(ivDetailProfilePic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_twitter, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

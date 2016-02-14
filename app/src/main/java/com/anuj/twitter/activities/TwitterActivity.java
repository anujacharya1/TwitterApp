package com.anuj.twitter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.anuj.twitter.R;

public class TwitterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);


        Toolbar twitterToolBar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(twitterToolBar);

//        twitterToolBar.setLogo(R.drawable.twitter_logo);
        twitterToolBar.setBackgroundColor(getResources().getColor(R.color.blue));

        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_twitter, menu);
        return super.onCreateOptionsMenu(menu);


    }



}

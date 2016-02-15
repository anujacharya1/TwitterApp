package com.anuj.twitter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anuj.twitter.R;
import com.anuj.twitter.TwitterApplication;
import com.anuj.twitter.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TwitterActivity extends AppCompatActivity {

    TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        //toolbar set
        setToolbar();

        //set the singleton client
        twitterClient = TwitterApplication.getRestClient(); //singleton

        Log.i("INFO","secret="+ twitterClient.checkAccessToken().getSecret());
        Log.i("INFO","token="+ twitterClient.checkAccessToken().getToken());

        //call the api and populate the timline
        populateTimeline();

    }

    private void setToolbar(){
        Toolbar twitterToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(twitterToolBar);
        twitterToolBar.setBackgroundColor(getResources().getColor(R.color.blue));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private void populateTimeline(){
        twitterClient.getUserTimeLine(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("INFO",response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();

                System.out.println("");
                Log.e("ERROR",errorResponse.toString());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_twitter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    /**
//     * This should take in MenuView and not View
//
//     */
//    public void composeIt(MenuView v){
//    }



}

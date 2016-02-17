package com.anuj.twitter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import com.anuj.twitter.R;
import com.anuj.twitter.TwitterApplication;
import com.anuj.twitter.TwitterClient;
import com.anuj.twitter.adapters.TwitterTimelineAdapter;
import com.anuj.twitter.models.Timeline;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TwitterActivity extends AppCompatActivity {

    TwitterClient twitterClient;

    @Bind(R.id.rvTimeline)
    public RecyclerView timeLineRecycleView;

    List<Timeline> timelines;

    TwitterTimelineAdapter twitterTimelineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        ButterKnife.bind(this);

        //toolbar set
        setToolbar();

        //set the singleton client
        twitterClient = TwitterApplication.getRestClient(); //singleton

        Log.i("INFO","secret="+ twitterClient.checkAccessToken().getSecret());
        Log.i("INFO","token="+ twitterClient.checkAccessToken().getToken());

        setupTheTimelineAdapter();
        //attach the adapter to the listView
        //call the api and populate the timline
        populateTimeline();

    }

    private void setupTheTimelineAdapter(){

        // set properties of recycler
        timeLineRecycleView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        timeLineRecycleView.setLayoutManager(linearLayoutManager);

        timelines = new ArrayList<>();
        twitterTimelineAdapter = new TwitterTimelineAdapter(timelines);

        // give our custom adapter to the recycler view
        timeLineRecycleView.setAdapter(twitterTimelineAdapter);
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


                Timeline timeline = new Timeline();
                List<Timeline> timelineList = timeline.getFromJsonArray(response);
                if(timelineList!=null && !timelineList.isEmpty()){
                    Log.i("INFO", "timelines= " + timelineList.toString());
                    timelines.addAll(timelineList);
                    //give to our adapter

                    int curSize = twitterTimelineAdapter.getItemCount();
                    twitterTimelineAdapter.notifyItemRangeInserted(curSize, timelines.size() - 1);

                }
                else{
                    Log.e("ERROR", "Did not got data from twitter API");
                }

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

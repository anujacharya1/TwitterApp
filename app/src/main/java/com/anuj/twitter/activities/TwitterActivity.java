package com.anuj.twitter.activities;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.anuj.twitter.adapters.EndlessRecyclerViewScrollListener;
import com.anuj.twitter.adapters.TwitterTimelineAdapter;
import com.anuj.twitter.dao.TimelineDO;
import com.anuj.twitter.dao.UserDO;
import com.anuj.twitter.dialogs.ComposeTweetDialog;
import com.anuj.twitter.models.Timeline;
import com.anuj.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.text.Editable;
import android.text.TextWatcher;

public class TwitterActivity extends AppCompatActivity {

    TwitterClient twitterClient;

    @Bind(R.id.rvTimeline)
    public RecyclerView timeLineRecycleView;

    List<Timeline> timelines;

    TwitterTimelineAdapter twitterTimelineAdapter;
    private android.support.v4.widget.SwipeRefreshLayout swipeContainer;

    private int sinceId = 0;


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
        Log.i("INFO", "token=" + twitterClient.checkAccessToken().getToken());

        setupTheTimelineAdapter();
        //attach the adapter to the listView
        //call the api and populate the timline
        populateTimeline(1);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Toast.makeText(getApplicationContext(), "SWIPE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTheTimelineAdapter(){

        // set properties of recycler
        timeLineRecycleView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        timeLineRecycleView.setLayoutManager(linearLayoutManager);

        timelines = new ArrayList<>();
        twitterTimelineAdapter = new TwitterTimelineAdapter(timelines);


        //end less scroller
        timeLineRecycleView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int pageList, int totalItemsCount) {

                Log.i("INFO","totalItemsCount="+totalItemsCount);
                if (sinceId != pageList) {
                    Log.i("INFO","sinceId="+sinceId);
                    Log.i("INFO", "pageList=" + pageList);
                    populateTimeline(++pageList);
                }
                else{
                    Log.e("ERROR", "sinceId=" + sinceId);
                    Log.e("ERROR", "pageList=" + pageList);
                }
            }
        });

        // give our custom adapter to the recycler view
        timeLineRecycleView.setAdapter(twitterTimelineAdapter);
    }

    private void setToolbar(){
        Toolbar twitterToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(twitterToolBar);
        twitterToolBar.setBackgroundColor(getResources().getColor(R.color.blue));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private void populateTimeline(int sinceId){

        twitterClient.getUserTimeLine(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Timeline timeline = new Timeline();
                List<Timeline> timelineList = timeline.getFromJsonArray(response);
                if (timelineList != null && !timelineList.isEmpty()) {
                    Log.i("INFO", "timelines= " + timelineList.toString());
                    timelines.addAll(timelineList);
                    //give to our adapter

                    //save the table
                    saveToDB(timelineList);

                    int curSize = twitterTimelineAdapter.getItemCount();
                    twitterTimelineAdapter.notifyItemRangeInserted(curSize, timelines.size() - 1);

                } else {
                    Log.e("ERROR", "Did not got data from twitter API");
                }

                Log.i("INFO", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
            }
        }, sinceId);
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
                        showComponseTweetDialog();
                        return false;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showComponseTweetDialog(){

        //show the dialog framgment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        //set the dilaog framgment
        DialogFragment newFragment = ComposeTweetDialog.newInstance(new ComposeTweetDialog.ComposeTweetDialogListner() {
            @Override
            public void onTweet(String text) {

                // got the value from the dialog
                // put in the list view and refresh the adapter

                Log.i("INFO", "@@@@@@@@@@@@@@@@@@@@ "+text);

            }
        });
        newFragment.show(ft, "TWEET_DIALOG");
    }

    private void saveToDB(List<Timeline> timelines){

        for(Timeline timeline :  timelines){
//
//            User user = timeline.getUser();
//            UserDO userDO = new UserDO(user.getName(), user.getProfileImg(), user.getScreenName());
//            TimelineDO timelineDO = new TimelineDO(userDO, timeline.getText(), timeline.getCreatedAt());
//            timelineDO.save();
        }
    }
}

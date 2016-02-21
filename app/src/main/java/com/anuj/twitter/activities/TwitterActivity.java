package com.anuj.twitter.activities;

import android.content.Intent;
import android.os.Parcel;
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
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.anuj.twitter.DBHelper;
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
import org.parceler.Parcels;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.text.Editable;
import android.text.TextWatcher;

public class TwitterActivity extends AppCompatActivity {

    TwitterClient twitterClient;

    @Bind(R.id.rvTimeline)
    public RecyclerView timeLineRecycleView;

    LinearLayoutManager linearLayoutManager;

    List<Timeline> timelines;

    TwitterTimelineAdapter twitterTimelineAdapter;
    private android.support.v4.widget.SwipeRefreshLayout swipeContainer;

    private int sinceId = 0;

    // based on the twitter doc send the first time null and subsequent the last one
    // https://dev.twitter.com/rest/public/timelines
    Long max_id = null;

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

        // get the cached timeline
        List<TimelineDO> cachedTimeLine = DBHelper.getAllInDescOfDate();
        if(cachedTimeLine!=null && !cachedTimeLine.isEmpty()){
           populateTheContentFromCachedDatabse(cachedTimeLine);
        }

        //Step1: try to get the data from database




        //attach the adapter to the listView
        //call the api and populate the timline
        //remember the first time is maxID is null
        populateTimeline(1, max_id);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //set the maxId to zero
                max_id = null;
                timelines.clear(); //clear list
                twitterTimelineAdapter.notifyDataSetChanged();
                populateTimeline(1, max_id);
            }
        });
    }

    private void populateTheContentFromCachedDatabse(List<TimelineDO> cachedTimeLine){

        Log.i("INFO", "+++++++++++GOING TO GET THE CACHED ENTRIES+++++++++++");
        // if there are entries cached in the database get it from there
        // and then populate more based on the last id cached in the database
        // so the user can progress faster

        // for people to get the latest let them refresh layout

        // timelines will be zero just convert TimelineDO to Timeline and poulate the adapter
        for(TimelineDO timelineDO : cachedTimeLine){
            UserDO userDO = timelineDO.getUser();
            Timeline timeline = new Timeline();
            timeline.setCreatedAt(timelineDO.getCreatedAtFromTwitter());
            timeline.setText(timelineDO.getText());

            User user = new User();
            user.setScreenName(userDO.getScreenName());
            user.setName(userDO.getName());
            user.setProfileImg(userDO.getProfileImg());
            timeline.setUser(user);

            timelines.add(timeline);
        }

        int curSize = twitterTimelineAdapter.getItemCount();
        twitterTimelineAdapter.notifyItemRangeInserted(curSize, timelines.size() - 1);


    }
    private void setupTheTimelineAdapter(){

        // set properties of recycler
        timeLineRecycleView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        timeLineRecycleView.setLayoutManager(linearLayoutManager);

        timelines = new LinkedList<>();
        twitterTimelineAdapter = new TwitterTimelineAdapter(timelines);

        twitterTimelineAdapter.setOnItemClickListener(new TwitterTimelineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Timeline timeline = timelines.get(position);

                Intent i = new Intent(TwitterActivity.this, TweetDetailActivity.class);
                i.putExtra("timeline", Parcels.wrap(timeline));
                startActivity(i);
            }
        });

        //end less scroller
        timeLineRecycleView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int pageList, int totalItemsCount) {

                Log.i("INFO", "totalItemsCount=" + totalItemsCount);
                if (sinceId != pageList) {
                    Log.i("INFO", "sinceId=" + sinceId);
                    Log.i("INFO", "pageList=" + pageList);
                    populateTimeline(++pageList, max_id);
                } else {
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

    private void populateTimeline(int sinceId, final Long maxId){

        twitterClient.getUserTimeLine(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Timeline timeline = new Timeline();
                List<Timeline> timelineList = timeline.getFromJsonArray(response);
                if (timelineList != null && !timelineList.isEmpty()) {
                    Log.i("INFO", "timelines= " + timelineList.toString());
                    //if maxId is null i.e the first time we call add all the result to the list
                    if (maxId == null) {
                        max_id = timelineList.get(timelineList.size() - 1).getId();
                        Log.i("INFO", "First Time going to set the max_id=" + max_id);
                        timelines.addAll(timelineList);
                        Log.i("INFO", "Got from twitter API count = " + timelines.size());

                    } else {

                        Log.i("INFO", "Subsequent ime going to set the max_id=" + max_id);

                        // remove the first element as it's going to be duplicate based on
                        // the twitter doc
                        timelineList.remove(0);
                        max_id = timelineList.get(timelineList.size() - 1).getId();
                        timelines.addAll(timelineList);
                    }

                    //save the table
                    saveToDB(timelineList);

                    List<TimelineDO> timelineDOs = DBHelper.getAllInDescOfDate();
                    Log.i("INFO", "Stored in database count = "+timelineDOs.size());

                    int curSize = twitterTimelineAdapter.getItemCount();
                    twitterTimelineAdapter.notifyItemRangeInserted(curSize, timelines.size() - 1);

                    swipeContainer.setRefreshing(false);
                } else {
                    Log.e("ERROR", "Did not got data from twitter API");
                }

                Log.i("INFO", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
                Log.e("ERROR", "GOT THE ERROR FROM THE TWITTER");
            }
        }, sinceId, max_id);
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
                // populate the listview if the text is not empty
                if(text!=null && !text.isEmpty()){
                    postTheUserTweetOnTheList(text);
                }

            }
        });
        newFragment.show(ft, "TWEET_DIALOG");
    }

    private void postTheUserTweetOnTheList(String tweet){
        twitterClient.postTweet(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Timeline timeline = new Timeline();
                timeline = timeline.getFromJsonObject(response);

                //save in table
                DBHelper.save(timeline);

                //insert item on the top of the list
                timelines.add(0, timeline);
                twitterTimelineAdapter.notifyItemInserted(0);

                //scroll to the top when the item is added
                linearLayoutManager.scrollToPosition(0);
                Log.i("INFO", timeline.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", errorResponse.toString());
                throwable.printStackTrace();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, tweet);
    }

    private void saveToDB(List<Timeline> timelines){
        ActiveAndroid.beginTransaction();
        try{
            for(Timeline timeline :  timelines){
                DBHelper.save(timeline);
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            ActiveAndroid.endTransaction();
        }

    }
}

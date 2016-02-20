package com.anuj.twitter;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Ltyjlnn9iMwD6RKl5LouOLHp8";       // Change this
	public static final String REST_CONSUMER_SECRET = "oLiTMr3NBkKkU2RoTcv0DNZtXNdlF69gIBRNFGVfRyztcOgmdN"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	//	user_timeline.json

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getUserTimeLine(AsyncHttpResponseHandler handler, Integer sinceId, Long maxId){

		String apiUrl = getApiUrl("statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("since_id", sinceId);
		params.put("count", 25);

		if(maxId!=null){
			params.put("max_id", maxId);
		}

		Log.i("INFO", "apiUrl==" + apiUrl);
		Log.i("INFO", "params==" + params);
		Log.i("INFO", "access_token==" + getClient().getAccessToken().getToken());
		getClient().get(apiUrl, params, handler);
	}


	public void postTweet(AsyncHttpResponseHandler handler, String tweet){

		String apiUrl = getApiUrl("statuses/update.json");

		RequestParams params = new RequestParams();
		params.put("status", tweet);
		Log.i("INFO", "apiUrl==" + apiUrl);
		Log.i("INFO", "params==" + params);
		Log.i("INFO", "access_token==" + getClient().getAccessToken().getToken());
		getClient().post(apiUrl, params, handler);

	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
package com.anuj.twitter.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anujacharya on 2/13/16.
 */


public class Timeline {

    @SerializedName("user")
    private User user;

    private String text;

    @SerializedName("created_at")
    private String createdAt;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Timeline> getFromJsonArray(JSONArray timelinesJsonArray){

        try{
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            List<Timeline> timeLines = new ArrayList<>();

            for(int i=0; i< timelinesJsonArray.length(); i++){

                String timelineStr = timelinesJsonArray.getJSONObject(i).toString();
                JsonObject timelineObj = parser.parse(timelineStr).getAsJsonObject();
                Timeline timeline =  gson.fromJson(timelineObj, Timeline.class);
                timeLines.add(timeline);
            }

            return timeLines;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "user=" + user +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

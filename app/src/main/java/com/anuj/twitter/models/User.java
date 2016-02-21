package com.anuj.twitter.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by anujacharya on 2/15/16.
 */
@Parcel
public class User {

    Long id;

    String name;

    @SerializedName("profile_image_url")
    String profileImg;

    @SerializedName("screen_name")
    private String screenName;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }
}

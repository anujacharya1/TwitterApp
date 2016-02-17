package com.anuj.twitter.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anujacharya on 2/15/16.
 */
public class User {

    String name;

    @SerializedName("profile_image_url")
    String profileImg;

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }
}

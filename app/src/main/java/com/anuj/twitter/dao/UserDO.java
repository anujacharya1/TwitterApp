package com.anuj.twitter.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;


import java.io.Serializable;

/**
 * Created by anujacharya on 2/17/16.
 */
@Table(name = "User")
public class UserDO extends Model implements Serializable {

    private String name;

    private String profileImg;

    private String screenName;

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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public UserDO(){
        super();
    }

    public UserDO(String name, String profileImg, String screenName) {
        this.name = name;
        this.profileImg = profileImg;
        this.screenName = screenName;
    }
}

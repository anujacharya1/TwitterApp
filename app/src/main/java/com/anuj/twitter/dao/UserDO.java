package com.anuj.twitter.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


import java.io.Serializable;

/**
 * Created by anujacharya on 2/17/16.
 */
@Table(name = "User")
public class UserDO extends Model {

    @Column(name = "_id", index =true)
    private Long _id;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "screen_name")
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public UserDO(){
        super();
    }

    public UserDO(Long id, String name, String profileImg, String screenName) {
        super();
        this._id = id;
        this.name = name;
        this.profileImg = profileImg;
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "mId=" +super.getId() +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }
}

package com.anuj.twitter.dao;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.anuj.twitter.models.Timeline;
import com.anuj.twitter.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anujacharya on 2/17/16.
 */
@Table(name = "Timeline")
public class TimelineDO extends Model{
    @Column(name = "_id",  index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private Long _id;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private UserDO user;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private String createdAt;

    public  TimelineDO(){
    }

    public TimelineDO(Long id, UserDO user, String text, String createdAt){
        super();
        this._id = id;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
    }


    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
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
    public static TimelineDO getRandom() {
        return new Select().from(TimelineDO.class).orderBy("RANDOM()").executeSingle();
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "TimelineDO{" +
                "mId=" +super.getId() +
                "_id=" + _id +
                "user=" + user +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

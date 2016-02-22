package com.anuj.twitter;

import android.util.Log;

import com.activeandroid.query.Select;
import com.anuj.twitter.dao.TimelineDO;
import com.anuj.twitter.dao.UserDO;
import com.anuj.twitter.models.Timeline;
import com.anuj.twitter.models.User;

import java.sql.Time;
import java.util.List;

/**
 * Created by anujacharya on 2/20/16.
 */
public class DBHelper {

    public static void save(Timeline timeline){
        User user = timeline.getUser();
        UserDO userDO = new UserDO(user.getId(), user.getName(), user.getProfileImg(),user.getScreenName() );
        userDO.save();

        TimelineDO timelineDO = new TimelineDO(timeline.getId(), userDO, timeline.getText(), timeline.getCreatedAt() );
        timelineDO.save();
    }

    public static List<TimelineDO> getAllInDescOfDate(){
        return new Select().all()
                .from(TimelineDO.class)
                .orderBy("created_at DESC")
                .execute();
    }
}

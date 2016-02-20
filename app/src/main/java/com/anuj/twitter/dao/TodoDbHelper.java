//package com.anuj.twitter.dao;
//
//import com.activeandroid.query.Select;
//import com.anuj.twitter.models.Timeline;
//
//import java.util.List;
//
///**
// * Created by anujacharya on 2/17/16.
// */
//public class TodoDbHelper{
//    private static final String TAG = TodoDbHelper.class.getSimpleName();
//
//    public List<Timeline> getAll() {
//        List<Timeline> timelineList =  new Select()
//                .from(Timeline.class)
//                .execute();
//
//        return timelineList;
//    }
//
//    public void deleteRecord(Timeline timeline){
//        Timeline item = Timeline.load(Timeline.class, timeline.getId());
//        item.delete();
//    }
//
////    public Timeline updateTodo(String value, String priority, String date, int id){
////        Todo item = Todo.load(Todo.class, id);
////        item.setValue(value);
////        item.setPriority(priority);
////        item.setDate(date);
////        item.save();
////        return item;
////    }
//
//    public Timeline saveToTable(TimelineDO timeline){
//
////        Timeline todo = new Todo();
////        todo.setValue(name);
////        todo.setPriority(priority);
////        todo.setDate(date);
//        timeline.save();
//        return timeline;
//    }
//}

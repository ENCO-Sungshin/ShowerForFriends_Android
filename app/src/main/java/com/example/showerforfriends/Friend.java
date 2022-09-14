package com.example.showerforfriends;

public class Friend {
    String friend_name;
    Integer use_time;
    Integer friend_img;

    public Friend(String friend_name, Integer use_time, Integer friend_img) {
        this.friend_name = friend_name;
        this.use_time = use_time;
        this.friend_img = friend_img;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public Integer getUse_time() {
        return use_time;
    }

    public Integer getFriend_img() {
        return friend_img;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public void setUse_time(Integer use_time) {
        this.use_time = use_time;
    }

    public void setFriend_img(Integer friend_img) {
        this.friend_img = friend_img;
    }
}

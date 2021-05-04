package io.sen.canteenia.models;

public class ChatMessage {

    String userid;
    String username;
    String message;
    String Tag;

    public ChatMessage() {
    }

    public ChatMessage(String userid, String username, String message) {
        this.userid = userid;
        this.username = username;
        this.message = message;
    }

    public ChatMessage(String userid, String username, String message, String tag) {
        this.userid = userid;
        this.username = username;
        this.message = message;
        Tag = tag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}

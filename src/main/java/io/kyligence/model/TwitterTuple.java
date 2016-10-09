package io.kyligence.model;

import twitter4j.Status;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiefan on 16-10-9.
 */
public class TwitterTuple implements Serializable{

    private long id;

    private Date createdAt;

    private String source;

    private int favoriteCount;

    private int retweetCount;

    private String lang;

    private String userTimezone;

    private String hashTag;

    public TwitterTuple(){};

    public TwitterTuple(Status status){
        this.id = status.getId();
        this.createdAt = status.getCreatedAt();
        this.source = status.getSource();
        this.favoriteCount = status.getFavoriteCount();
        this.retweetCount = status.getRetweetCount();
        this.lang  = status.getLang();
        this.userTimezone = status.getUser().getTimeZone();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(String userTimezone) {
        this.userTimezone = userTimezone;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
}

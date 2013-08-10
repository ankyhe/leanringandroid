package com.gmail.at.gerystudio.criminalIntent.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-10
 * Time: PM8:22
 * To change this template use File | Settings | File Templates.
 */
public class Crime {

    private UUID uuid;
    private String title;
    private long datetime;
    private boolean solved;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public Crime(String aName) {
        uuid = UUID.randomUUID();
        title = aName;
        datetime = (new Date()).getTime();
        solved = false;
    }

    public Crime() {
        this("");
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String aTitle) {
        title = aTitle;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getDatetimeStr() {
        Date date = new Date(getDatetime());
        return dateFormat.format(date);
    }

    public void setDatetime(Date date) {
        datetime = date.getTime();
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean aSolved) {
        solved = aSolved;
    }

    @Override
    public String toString() {
        return title;
    }
}

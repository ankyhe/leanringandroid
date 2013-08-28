package com.gmail.at.gerystudio.criminalIntent.model;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Photo photo;
    private String suspect;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public Crime(JSONObject json) throws JSONException {
        uuid = UUID.fromString(json.getString("uuid"));
        title = json.getString("title");
        solved = json.getBoolean("solved");
        datetime = json.getLong("datetime");
        String fileName = json.getString("photo");
        if (fileName != null) {
            photo = new Photo(fileName);
        }
        String aSuspect = json.getString("suspect");
        if (aSuspect != null) {
            suspect = aSuspect;
        }
    }


    public Crime(String aName) {
        uuid = UUID.randomUUID();
        title = aName;
        datetime = (new Date()).getTime() - 24 * 3600 * 1000; // one day before
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid.toString());
            json.put("title", title);
            json.put("solved", solved);
            json.put("datetime", datetime);
            if (photo != null) {
                json.put("photo", photo.getFileName());
            }
            if (suspect != null) {
                json.put("suspect", suspect);
            }
        } catch (JSONException e) {
            json = new JSONObject();
        }
        return json;
    }

    public String toReport() {
        Date date = new Date(datetime);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return String.format("Title:%s\nDateTime:%s\nSuspect:%s", title, df.format(date), (suspect != null ? suspect : "Unknown"));
    }

    @Override
    public String toString() {
        return title;
    }
}

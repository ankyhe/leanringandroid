package com.gmail.at.ankyhe.photogallery.model;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 8/30/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class GalleryItem {

    private String caption;
    private String id;
    private String url;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return caption;
    }

}

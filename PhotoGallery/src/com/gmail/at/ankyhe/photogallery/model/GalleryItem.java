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
    private String owner;


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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhotoPageUrl() {
        return String.format("http://www.flickr.com/photos/%s/%s", owner, id);
    }

    @Override
    public String toString() {
        return caption;
    }

}

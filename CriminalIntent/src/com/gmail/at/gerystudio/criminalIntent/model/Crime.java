package com.gmail.at.gerystudio.criminalIntent.model;

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
    private String name;

    public Crime(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public Crime() {
        this("");
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}

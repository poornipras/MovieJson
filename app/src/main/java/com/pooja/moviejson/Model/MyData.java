package com.pooja.moviejson.Model;

import android.content.Context;

/**
 * Created by Pooja on 2/11/2017.
 */

public class MyData {

    String name;
    int id,vote_count;

    public String getName()
    {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.applaudstudios.android.moviesproject2.model.trailer;

/**
 * Created by wjplaud83 on 6/19/17.
 */

public class movieYoutubeModel {

    private String id;

    private Results[] results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    @Override
    public String toString() {

        return "Class [id = " + id + ", results = " + results + "]";
    }
}


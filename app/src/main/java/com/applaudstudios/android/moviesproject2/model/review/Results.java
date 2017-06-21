package com.applaudstudios.android.moviesproject2.model.review;

/**
 * Created by wjplaud83 on 6/19/17.
 */

public class Results {
    private String content;

    private String id;

    private String author;

    private String url;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ClassPo [content = " + content + ", id = " + id + ", author = " + author + ", url = " + url + "]";
    }
}

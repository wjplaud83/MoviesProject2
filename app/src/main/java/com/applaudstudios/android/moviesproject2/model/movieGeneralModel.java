package com.applaudstudios.android.moviesproject2.model;

import java.io.Serializable;

/**
 * Created by wjplaud83 on 6/19/17.
 */

public class movieGeneralModel implements Serializable {
    String mTitle;
    String mThumbnail;
    String mVote;
    String mId;
    String mPeople;
    String mReleaseDate;
    String mOverview;
    String mReview;

    public movieGeneralModel(String mTitle, String mThumbnail, String mVote, String mId, String mPeople, String mReleaseDate, String mOverview) {
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mVote = mVote;
        this.mId = mId;
        this.mPeople = mPeople;
        this.mReleaseDate = mReleaseDate;
        this.mOverview = mOverview;
    }

    public String getmReview() {
        return this.mReview;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }

    public String getmOverview() {
        return this.mOverview;
    }

    public String getmReleaseDate() {
        return this.mReleaseDate;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getThumbnail() {
        String url = "http://image.tmdb.org/t/p/w185/" + this.mThumbnail;
        return url;
    }

    public String getmId() {
        return this.mId;
    }

    public String getmPeople() {
        return this.mPeople;
    }
    public String getmVote() {
        return this.mVote;
    }
}

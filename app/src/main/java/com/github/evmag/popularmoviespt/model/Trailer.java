package com.github.evmag.popularmoviespt.model;

public class Trailer {
    private String mId;
    private String mKey;
    private String mSite;

    public Trailer(String id, String key, String site) {
        mId = id;
        mKey = key;
        mSite = site;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }
}

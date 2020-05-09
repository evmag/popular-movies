package com.github.evmag.popularmoviespt.model;

public class Review {
    private String mId;
    private String mAuthor;
    private String mContent;

    public Review(String id, String author, String content) {
        mId = id;
        mAuthor = author;
        mContent = content;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}

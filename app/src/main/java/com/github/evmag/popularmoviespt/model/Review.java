package com.github.evmag.popularmoviespt.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Movie.class,
        parentColumns = "movie_id",
        childColumns = "review_id",
        onDelete = ForeignKey.CASCADE))
public class Review {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "review_id")
    private String mId;
    @ColumnInfo(name = "author")
    private String mAuthor;
    @ColumnInfo(name = "content")
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

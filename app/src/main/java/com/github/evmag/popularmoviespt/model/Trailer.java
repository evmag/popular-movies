package com.github.evmag.popularmoviespt.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Movie.class,
    parentColumns = "movie_id",
    childColumns = "trailer_id",
    onDelete = ForeignKey.CASCADE))
public class Trailer {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "trailer_id")
    private String mId;
    @ColumnInfo(name = "key")
    private String mKey;
    @ColumnInfo(name = "site")
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

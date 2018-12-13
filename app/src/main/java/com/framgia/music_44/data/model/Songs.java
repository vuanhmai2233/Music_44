package com.framgia.music_44.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Songs implements Parcelable {

    private String mNameSong;
    private String mNameArtist;
    private String mImage;
    private String mDuration;
    private String mUri;
    private String mId;

    public Songs(String nameSong, String nameArtist, String image, String duration, String uri,
            String id) {
        mNameSong = nameSong;
        mNameArtist = nameArtist;
        mImage = image;
        mDuration = duration;
        mUri = uri;
        mId = id;
    }

    protected Songs(Parcel in) {
        mNameSong = in.readString();
        mNameArtist = in.readString();
        mImage = in.readString();
        mDuration = in.readString();
        mUri = in.readString();
        mId = in.readString();
    }

    public String getNameSong() {
        return mNameSong;
    }

    public String getNameArtist() {
        return mNameArtist;
    }

    public String getImage() {
        return mImage;
    }

    public String getDuration() {
        return mDuration;
    }

    public String getUri() {
        return mUri;
    }

    public String getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNameSong);
        dest.writeString(mNameArtist);
        dest.writeString(mImage);
        dest.writeString(mDuration);
        dest.writeString(mUri);
        dest.writeString(mId);
    }
    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };
}

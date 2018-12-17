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

    public Songs(SongsBuilder songsBuilder) {
        mNameSong = songsBuilder.mNameSong;
        mNameArtist = songsBuilder.mNameArtist;
        mImage = songsBuilder.mImage;
        mDuration = songsBuilder.mDuration;
        mUri = songsBuilder.mUri;
        mId = songsBuilder.mId;
    }

    protected Songs(Parcel in) {
        mNameSong = in.readString();
        mNameArtist = in.readString();
        mImage = in.readString();
        mDuration = in.readString();
        mUri = in.readString();
        mId = in.readString();
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

    public static class SongsBuilder {
        private String mNameSong;
        private String mNameArtist;
        private String mImage;
        private String mDuration;
        private String mUri;
        private String mId;

        public SongsBuilder() {
        }

        public SongsBuilder nameSong(String nameSong) {
            mNameSong = nameSong;
            return this;
        }

        public SongsBuilder nameArtist(String nameArtist) {
            mNameArtist = nameArtist;
            return this;
        }

        public SongsBuilder image(String image) {
            mImage = image;
            return this;
        }

        public SongsBuilder duration(String duration) {
            mDuration = duration;
            return this;
        }

        public SongsBuilder uri(String uri) {
            mUri = uri;
            return this;
        }

        public SongsBuilder id(String id) {
            mId = id;
            return this;
        }

        public Songs build() {
            return new Songs(this);
        }
    }

    public final class SongsEntry {
        public static final String TITLE = "title";
        public static final String LABLE_NAME = "label_name";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DURATION = "duration";
        public static final String STREAM_URL = "stream_url";
        public static final String ID = "id";
    }
}

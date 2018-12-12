package com.framgia.music_44.data.source;

import com.framgia.music_44.data.source.local.OnResultDataListener;

public class SongsRepository {
    private static SongsRepository sInstance;
    private SongDataSource.Local mLocalDataSource;

    private SongsRepository(SongDataSource.Local localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static SongsRepository getsInstance(SongDataSource.Local localDataSource) {
        if (sInstance == null) {
            sInstance = new SongsRepository(localDataSource);
        }
        return sInstance;
    }

    public void getData(OnResultDataListener songLocal) {
        mLocalDataSource.getSongsLocal(songLocal);
    }
}

package com.framgia.music_44.data.source;

import com.framgia.music_44.data.source.local.OnResultDataListener;
import com.framgia.music_44.data.source.remote.OnResultDataListenerRemote;

public class SongsRepository {

    private static SongsRepository sInstance;
    private SongDataSource.Local mLocalDataSource;
    private SongDataSource.Remote mRemoteDataSource;

    private SongsRepository(SongDataSource.Local localDataSource,
            SongDataSource.Remote remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static SongsRepository getsInstance(SongDataSource.Local localDataSource,
            SongDataSource.Remote remoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongsRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }

    public void getData(OnResultDataListener songLocal) {
        mLocalDataSource.getSongsLocal(songLocal);
    }

    public void getDataRemote(OnResultDataListenerRemote remote) {
        mRemoteDataSource.getSongsRemote(remote);
    }
}

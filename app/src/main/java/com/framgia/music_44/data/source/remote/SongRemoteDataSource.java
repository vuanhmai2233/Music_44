package com.framgia.music_44.data.source.remote;

import com.framgia.music_44.data.source.SongDataSource;
import com.framgia.music_44.data.source.remote.fetchjson.GetMusicDataJson;
import com.framgia.music_44.util.Constant;

public class SongRemoteDataSource implements SongDataSource.Remote {

    private static SongRemoteDataSource sInstance;

    public static SongRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new SongRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getSongsRemote(OnResultDataListenerRemote songRemote) {
        new GetMusicDataJson(songRemote).execute(Constant.BASE_URL);
    }
}

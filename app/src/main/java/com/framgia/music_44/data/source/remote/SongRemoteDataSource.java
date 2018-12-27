package com.framgia.music_44.data.source.remote;

import com.framgia.music_44.data.source.SongDataSource;
import com.framgia.music_44.data.source.remote.fetchjson.GetMusicDataJson;
import com.framgia.music_44.util.Constant;

public class SongRemoteDataSource implements SongDataSource.Remote {

    private static final String GENRES = "&genres=";
    private static final String SEARCH = "&q=";
    private static final String KIND_TRACK = "/tracks?";

    private static SongRemoteDataSource sInstance;

    public static SongRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new SongRemoteDataSource();
        }
        return sInstance;
    }

    private String getGenres(String genresType) {
        return KIND_TRACK + Constant.LINKED_PARTITION + GENRES + genresType;
    }

    private String getSearch(String searchType) {
        return KIND_TRACK + Constant.LINKED_PARTITION + SEARCH + searchType;
    }

    @Override
    public void getSongsRemote(OnResultDataListenerRemote resultListener, String genres) {
        new GetMusicDataJson(resultListener).execute(getGenres(genres));
    }

    @Override
    public void getSongsBySearch(OnResultDataListenerRemote resultListener, String search) {
        new GetMusicDataJson(resultListener).execute(getSearch(search));
    }
}

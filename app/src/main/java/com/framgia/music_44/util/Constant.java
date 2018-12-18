package com.framgia.music_44.util;

import com.framgia.music_44.BuildConfig;

public class Constant {
    public static final int SCREEN_HOME_ID = 0;
    public static final int SCREEN_GENRES_ID = 1;
    public static final int SCREEN_SEARCH_ID = 2;
    public static final String CLIENT_ID = "?client_id=" + BuildConfig.API_KEY;
    public static final int ONE = 1;
    private static final String HTTP = "https://api.soundcloud.com/tracks";
    private static final String LINKED_PARTITION = "&linked_partitioning=1&limit=10";
    public static final String BASE_URL = HTTP + CLIENT_ID + LINKED_PARTITION;
}

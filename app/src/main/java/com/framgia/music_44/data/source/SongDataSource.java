package com.framgia.music_44.data.source;

import com.framgia.music_44.data.source.local.OnResultDataListener;

public interface SongDataSource {
    interface Local {
        void getSongsLocal(OnResultDataListener songLocal);
    }
}

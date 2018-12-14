package com.framgia.music_44.data.source;

import com.framgia.music_44.data.source.local.OnResultDataListener;
import com.framgia.music_44.data.source.remote.OnResultDataListenerRemote;

public interface SongDataSource {
    interface Local {
        void getSongsLocal(OnResultDataListener songLocal);
    }

    interface Remote {
        void getSongsRemote(OnResultDataListenerRemote songRemote);
    }
}
